package gwf.util.task.sql;

import gwf.api.task.TaskExecutionResult;
import gwf.util.task.AbstractWorkflowTask;

import java.util.function.Consumer;

public class ExecuteSql extends AbstractWorkflowTask<SqlConfig> {

	private SqlConfigImpl config = null;

	@Override
	public void configure(Consumer<SqlConfig> c) {
		config = new SqlConfigImpl(getName());
		c.accept(config);
		config.validate();
	}

	@Override
	public TaskExecutionResult execute() {

		config.getJdbi().useHandle(handle ->
				config.getConsumers().forEach(
						stmt -> stmt.apply(handle)
				)
		);

		return null;
	}
}
