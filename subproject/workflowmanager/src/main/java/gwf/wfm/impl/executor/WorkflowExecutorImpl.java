package gwf.wfm.impl.executor;

import gwf.api.executor.WorkflowExecutor;
import gwf.api.task.TaskConfig;
import gwf.api.task.TaskExecutionResult;
import gwf.api.task.WorkflowTask;

import java.util.List;
import java.util.stream.Collectors;

public class WorkflowExecutorImpl implements WorkflowExecutor {

	@Override
	public List<TaskExecutionResult> execute(TaskConfig taskConfig) {
		return taskConfig.getTasks()
				.stream()
				.map(WorkflowTask::execute)
				.collect(Collectors.toList());
	}
}
