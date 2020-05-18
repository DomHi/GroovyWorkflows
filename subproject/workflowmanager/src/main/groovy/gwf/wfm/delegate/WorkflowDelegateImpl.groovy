package gwf.wfm.delegate

import gwf.api.delegate.WorkflowDelegateBase
import gwf.api.executor.ExecutorConfig
import gwf.api.task.TaskConfig
import gwf.api.workflow.WorkflowExecutionContext
import gwf.wfm.executor.ExecutorConfigImpl
import gwf.wfm.task.TaskConfigImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WorkflowDelegateImpl implements WorkflowDelegateBase {

    private Logger log

    private WorkflowExecutionContext ctx

    private ExecutorConfig executorConfig

    private TaskConfig taskConfig

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
        delegate(executorConfig, cl)
    }

    @Override
    void tasks(@DelegatesTo(value = TaskConfig, strategy = Closure.DELEGATE_FIRST) Closure<?> cl) {
        if(taskConfig == null) {
            taskConfig = new TaskConfigImpl()
        }
        delegate(taskConfig, cl)
    }

    TaskConfig getTaskConfig() {
        taskConfig
    }

    private void delegate(delegate, Closure<?> cl) {
        def clone = cl.rehydrate(delegate, this, this)
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone.call()
    }

    private void initLogging() {
        log = LoggerFactory.getLogger(loggerName)
    }

    private String getLoggerName() {
        /wfm.$ctx.workflowName/
    }
}
