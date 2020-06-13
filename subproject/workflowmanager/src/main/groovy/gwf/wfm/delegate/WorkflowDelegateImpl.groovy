package gwf.wfm.delegate

import gwf.api.delegate.WorkflowDelegateBase
import gwf.api.executor.ExecutorConfig
import gwf.api.task.TaskConfig
import gwf.api.task.WorkflowTask
import gwf.api.util.ClosureUtil
import gwf.api.workflow.WorkflowExecutionContext
import gwf.wfm.executor.ExecutorConfigImpl
import gwf.wfm.task.CdiTaskInstantiator
import gwf.wfm.task.TaskConfigImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WorkflowDelegateImpl implements WorkflowDelegateBase {

    private Logger log

    private WorkflowExecutionContext ctx

    private ExecutorConfig executorConfig

    private List<TaskConfig> taskConfigs = []

    WorkflowDelegateImpl(WorkflowExecutionContext ctx) {
        this.ctx = ctx
        initLogging()
    }

    @Override
    Logger getLog() {
        log
    }

    @Override
    WorkflowExecutionContext getContext() {
        ctx
    }

    @Override
    void executor(@DelegatesTo(value = ExecutorConfig, strategy = Closure.DELEGATE_FIRST) Closure<?> cl) {
        if(executorConfig == null) {
            executorConfig = new ExecutorConfigImpl()
        }
        ClosureUtil.delegateFirst(cl, executorConfig).call()
    }

    @Override
    void tasks(@DelegatesTo(value = TaskConfig, strategy = Closure.DELEGATE_FIRST) Closure<?> cl) {
        def newTasks = new TaskConfigImpl(new CdiTaskInstantiator())
        ClosureUtil.delegateFirst(cl, newTasks).call()
        taskConfigs.add(newTasks)
    }

    Collection<WorkflowTask> getTasks() {
        def tasks = []
        taskConfigs.each {
            tasks.add it.tasks
        }
        tasks
    }

    private void initLogging() {
        log = LoggerFactory.getLogger(loggerName)
    }

    private String getLoggerName() {
        /wfm.$ctx.workflowName/
    }
}
