package gwf.api.task.instance;

import gwf.api.task.WorkflowTask;
import gwf.api.workflow.WorkflowExecutionContext;

public interface TaskInstantiator {

	<T extends WorkflowTask> T create(Class<T> clazz, WorkflowExecutionContext ctx);
}
