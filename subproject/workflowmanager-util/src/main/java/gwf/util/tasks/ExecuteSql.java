package gwf.util.tasks;

import groovy.lang.Closure;
import gwf.api.task.TaskExecutionResult;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ExecuteSql extends AbstractWorkflowTask {

	private final List<String> statements = new ArrayList<>();

	public void sql(Closure<String> cl) {
		statements.add(cl.call());
	}

	@Override
	public TaskExecutionResult execute() {
		Jdbi.create(ds()).useHandle(
				handle ->
					statements.forEach(
							stmt -> handle.createUpdate(stmt).execute()
					)
		);
		return null;
	}

	private DataSource ds() {
		// TODO get DataSource from workflow context
		return null;
	}

	private Logger log() {
		// TODO get Logger from workflow context
		return null;
	}
}
