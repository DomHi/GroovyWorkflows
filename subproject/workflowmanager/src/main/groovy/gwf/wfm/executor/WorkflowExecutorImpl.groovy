package gwf.wfm.executor

import gwf.api.executor.WorkflowExecutor
import gwf.api.task.TaskConfig
import gwf.api.task.TaskExecutionResult

class WorkflowExecutorImpl implements WorkflowExecutor {

    @Override
    <T extends TaskExecutionResult> List<T> execute(TaskConfig taskConfig) {
        taskConfig.tasks*.execute()
    }
}
