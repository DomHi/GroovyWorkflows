package gwf.api.executor.provider;

import gwf.api.task.WorkflowTask;

public interface TaskProvider extends Iterable<WorkflowTask<?>> {

	int getTaskCount();
}
