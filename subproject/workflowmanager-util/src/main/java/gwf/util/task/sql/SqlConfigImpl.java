package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GString;
import gwf.api.WorkflowManagerException;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.context.WorkflowContext;
import gwf.util.task.context.DefaultDatabaseConfig;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class SqlConfigImpl implements SqlConfig {

	private final String taskName;

	private final List<HandleConsumer> consumers = new ArrayList<>();

	private Jdbi jdbi = null;

	protected SqlConfigImpl(String taskName) {
		this.taskName = taskName;
	}

	protected Jdbi getJdbi() {
		return jdbi;
	}

	protected List<HandleConsumer> getConsumers() {
		return consumers;
	}

	protected void validate() {
		checkJdbi();
	}

	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public void jdbi(@DelegatesTo(Jdbi.class) Closure<?> cl) {
		jdbi(defaultDataSource(), cl);
	}

	@Override
	public void jdbi(DataSource ds, @DelegatesTo(Jdbi.class) Closure<?> cl) {
		if (jdbi != null) {
			throw new WorkflowManagerException("Jdbi already configured");
		}

		jdbi = Jdbi.create(ds);
		ClosureUtil.delegateFirst(cl, jdbi).call();
	}

	@Override
	public void update(@DelegatesTo(UpdateStatement.class) Closure<?> cl) {
		UpdateStatement stmt = new UpdateStatement();
		Object ret = ClosureUtil.delegateFirst(cl, stmt).call();
		if (ret instanceof String || ret instanceof GString) {
			stmt.setStatement(ret.toString());
		}
		consumers.add(stmt);
		Object ret = ClosureUtil.delegateFirst(cl, stmt).call();
		if (ret instanceof String || ret instanceof GString) {
			stmt.setStatement(ret.toString());
		}
		consumers.add(stmt);
	}

	@Override
	public <T> Select<T> select(Class<T> clazz,
	                            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl) {
		Select<T> s = new Select<>(clazz, false);
		return internalSelect(s, cl);
	}

	@Override
	public <T> Select<T> selectBean(Class<T> clazz,
	                                @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl) {
		Select<T> s = new Select<>(clazz, true);
		return internalSelect(s, cl);
	}

	@Override
	public void handle(Closure<?> cl) {
		consumers.add(new HandleConsumer() {
			@Override
			protected void apply(Handle handle) {
				cl.call(handle);
			}
		});
	}

	private DataSource defaultDataSource() {
		return WorkflowContext.get(DefaultDatabaseConfig.class).getDefaultDs();
	}

	/**
	 * Use default jdbi config if it is not explicitely configured
	 */
	private void checkJdbi() {
		if (jdbi == null) {
			jdbi = Jdbi.create(defaultDataSource());
		}
	}

	private <T> Select<T> internalSelect(Select<T> select, Closure<?> cl) {
		Object ret = ClosureUtil.delegateFirst(cl, select).call();
		if (ret instanceof String || ret instanceof GString) {
			select.setStatement(ret.toString());
		}
		consumers.add(select);
		return select;
	}
}
