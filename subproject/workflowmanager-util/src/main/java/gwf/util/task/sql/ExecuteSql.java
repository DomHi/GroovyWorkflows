package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GString;
import gwf.api.WorkflowManagerException;
import gwf.api.task.TaskExecutionResult;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.context.WorkflowContext;
import gwf.util.task.AbstractWorkflowTask;
import gwf.util.task.context.DefaultDatabaseConfig;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ExecuteSql extends AbstractWorkflowTask {

	private static final Logger log = LoggerFactory.getLogger(ExecuteSql.class);

	private final List<Statement> statements = new ArrayList<>();

	private Jdbi jdbi = null;

	@Override
	public TaskExecutionResult execute() {
		checkJdbi();

		jdbi.useHandle(handle ->
				statements.forEach(
						stmt -> stmt.execute(handle)
				)
		);

		return null;
	}

	public void jdbi(@DelegatesTo(Jdbi.class) Closure<?> cl) {
		jdbi(defaultDataSource(), cl);
	}

	public void jdbi(DataSource ds, @DelegatesTo(Jdbi.class) Closure<?> cl) {
		if (jdbi != null) {
			throw new WorkflowManagerException("Jdbi already configured");
		}

		Jdbi delegate = Jdbi.create(ds);
		ClosureUtil.delegateFirst(cl, delegate).call();
		jdbi = delegate;
	}

	public void sql(@DelegatesTo(SimpleStatement.class) Closure<?> cl) {
		SimpleStatement stmt = new SimpleStatement();
		Object ret = ClosureUtil.delegateFirst(cl, stmt).call();
		if (ret instanceof String || ret instanceof GString) {
			stmt.setStatement(ret.toString());
		}
		statements.add(stmt);
	}

	public <T> Select<T> select(Class<T> clazz,
	                            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl) {
		Select<T> s = new Select<>(clazz, false);
		return internalSelect(s, cl);
	}

	public <T> Select<T> selectBean(Class<T> clazz,
	                                @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl) {
		Select<T> s = new Select<>(clazz, true);
		return internalSelect(s, cl);
	}

	private <T> Select<T> internalSelect(Select<T> select, Closure<?> cl) {
		Object ret = ClosureUtil.delegateFirst(cl, select).call();
		if (ret instanceof String || ret instanceof GString) {
			select.setStatement(ret.toString());
		}
		statements.add(select);
		return select;
	}

	private DataSource defaultDataSource() {
		return WorkflowContext.get(DefaultDatabaseConfig.class).getDefaultDs();
	}

	/**
	 * Use default jdbi config if it is not explicitely configured
	 */
	private void checkJdbi() {
		if (jdbi == null) {
			jdbi = Jdbi.create(defaultDataSource()).setSqlLogger(new SqlLogger() {
				@Override
				public void logBeforeExecution(StatementContext context) {
					log.info("Execute: {}", context.getRenderedSql());
				}
			});
		}
	}
}
