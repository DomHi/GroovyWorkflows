package gwf.util.task.sql.config.impl;

import groovy.lang.Closure;
import groovy.lang.GString;
import gwf.api.WorkflowManagerException;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.context.WorkflowContext;
import gwf.util.task.context.ContextualDataSources;
import gwf.util.task.sql.BatchStatement;
import gwf.util.task.sql.HandleConsumer;
import gwf.util.task.sql.Select;
import gwf.util.task.sql.UpdateStatement;
import gwf.util.task.sql.config.SelectConfig;
import gwf.util.task.sql.config.SqlConfig;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SqlConfigImpl implements SqlConfig {

	private final String taskName;

	private final List<HandleConsumer> consumers = new ArrayList<>();

	private Jdbi jdbi = null;

	public SqlConfigImpl(String taskName) {
		this.taskName = taskName;
	}

	public Jdbi getJdbi() {
		return jdbi;
	}

	public List<HandleConsumer> getConsumers() {
		return consumers;
	}

	public void validate() {
		checkJdbi();
	}

	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public void jdbi(Closure<?> cl) {
		jdbi(defaultDataSource(), cl);
	}

	@Override
	public void jdbi(DataSource ds, Closure<?> cl) {
		if (jdbi != null) {
			throw new WorkflowManagerException("Jdbi already configured");
		}

		jdbi = Jdbi.create(ds);
		ClosureUtil.delegateFirst(cl, jdbi).call();
	}

	@Override
	public void update(Closure<?> cl) {
		UpdateConfigImpl cfg = new UpdateConfigImpl();
		Object ret = ClosureUtil.delegateFirst(cl, cfg).call();
		if (ret instanceof String || ret instanceof GString) {
			cfg.setStatement(ret.toString());
		}
		consumers.add(new UpdateStatement(cfg));
	}

	@Override
	public void insertBatch(Closure<?> cl) {
		BatchConfigImpl cfg = new BatchConfigImpl();
		Object ret = ClosureUtil.delegateFirst(cl, cfg).call();
		if (ret instanceof String || ret instanceof GString) {
			cfg.setStatement(ret.toString());
		}
		consumers.add(new BatchStatement(cfg));
	}

	@Override
	public <T> SelectConfig.Then<T> select(Class<T> clazz, Closure<?> cl) {
		SelectConfigImpl<T> config = new SelectConfigImpl<>(clazz, false);
		configure(config, cl);
		consumers.add(new Select<>(config));
		return config.getThen();
	}

	@Override
	public <T> SelectConfig.Then<T> selectBean(Class<T> clazz, Closure<?> cl) {
		SelectConfigImpl<T> config = new SelectConfigImpl<>(clazz, true);
		configure(config, cl);
		consumers.add(new Select<>(config));
		return config.getThen();
	}

	@Override
	public void handle(Closure<?> cl) {
		consumers.add(cl::call);
	}

	private DataSource defaultDataSource() {
		return WorkflowContext.get(ContextualDataSources.class).getDefault()
				.orElseThrow(() -> new WorkflowManagerException("No default DataSource configured."));
	}

	/**
	 * Use default jdbi config if it is not explicitely configured
	 */
	private void checkJdbi() {
		if (jdbi == null) {
			jdbi = Jdbi.create(defaultDataSource());
		}
	}

	private <T> void configure(SelectConfigImpl<T> config, Closure<?> cl) {
		Object ret = ClosureUtil.delegateFirst(cl, config).call();
		if (ret instanceof String || ret instanceof GString) {
			config.setStatement(ret.toString());
		}
	}
}
