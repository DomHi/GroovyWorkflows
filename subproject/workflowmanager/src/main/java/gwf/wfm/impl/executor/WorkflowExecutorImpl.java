package gwf.wfm.impl.executor;

import gwf.api.executor.WorkflowExecutor;
import gwf.api.executor.provider.TaskProvider;
import gwf.api.task.WorkflowTask;

import java.util.Map;

public class WorkflowExecutorImpl implements WorkflowExecutor {

	@Override
	public void execute(TaskProvider tasks, Map<String, Object> properties) {
		tasks.forEach(WorkflowTask::execute);
	}
}
