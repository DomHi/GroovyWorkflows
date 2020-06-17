package gwf.api.executor;

import gwf.api.task.TaskConfig;
import gwf.api.task.TaskExecutionResult;

import java.util.List;

public interface WorkflowExecutor {

	List<TaskExecutionResult> execute(TaskConfig taskConfig);
}
