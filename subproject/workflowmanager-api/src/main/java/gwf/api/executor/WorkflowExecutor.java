package gwf.api.executor;

import gwf.api.task.TaskExecutionResult;
import gwf.api.task.WorkflowTask;

import java.util.Collection;
import java.util.List;

public interface WorkflowExecutor {

    List<TaskExecutionResult> execute(Collection<WorkflowTask> tasks);
}
