package gwf.util.task;

import groovy.lang.Closure;
import gwf.api.task.TaskExecutionResult;
import gwf.api.workflow.context.WorkflowContext;
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

	private final List<String> statements = new ArrayList<>();

	public void sql(Closure<String> cl) {
		statements.add(cl.call());
	}

	@Override
	public TaskExecutionResult execute() {
		jdbi().useHandle(
				handle ->
					statements.forEach(
							stmt -> handle.createUpdate(stmt).execute()
					)
		);
		return null;
	}

	private Jdbi jdbi() {
		return Jdbi.create(ds()).setSqlLogger(new SqlLogger() {
			@Override
			public void logBeforeExecution(StatementContext context) {
				log.info("Execute: {}", context.getRenderedSql());
			}
		});
	}

	private DataSource ds() {
		return WorkflowContext.get(DefaultDatabaseConfig.class).getDefaultDs();
	}
}
