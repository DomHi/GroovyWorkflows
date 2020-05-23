package gwf.api.executor;

import gwf.api.task.TaskConfig;
import gwf.api.task.TaskExecutionResult;

import java.util.List;

public interface WorkflowExecutor {

	<T extends TaskExecutionResult> List<T> execute(TaskConfig taskConfig);
}
