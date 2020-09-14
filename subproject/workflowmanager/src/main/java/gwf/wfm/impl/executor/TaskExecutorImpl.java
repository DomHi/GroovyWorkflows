package gwf.wfm.impl.executor;

import gwf.api.executor.TaskExecutor;
import gwf.api.executor.provider.TaskProvider;
import gwf.api.task.WorkflowTask;

import java.util.Map;

public class TaskExecutorImpl implements TaskExecutor {

	@Override
	public void execute(TaskProvider tasks, Map<String, Object> properties) {
		tasks.forEach(WorkflowTask::execute);
	}
}
