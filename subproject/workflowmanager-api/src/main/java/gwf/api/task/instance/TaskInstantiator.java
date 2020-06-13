package gwf.api.task.instance;

import gwf.api.task.WorkflowTask;

public interface TaskInstantiator {

	<T extends WorkflowTask> T create(Class<T> clazz);
}
