package gwf.wfm.delegate

import gwf.api.delegate.WorkflowDelegateBase
import gwf.api.task.TaskConfig
import gwf.api.workflow.WorkflowExecutionContext
import gwf.wfm.task.TaskConfigImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WorkflowDelegateImpl implements WorkflowDelegateBase {

    private Logger log;

    private WorkflowExecutionContext ctx;

    private TaskConfig taskConfig;

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
    void tasks(Closure<?> cl) {
        if(taskConfig == null) {
            taskConfig = new TaskConfigImpl()
        }

        def clone = cl.rehydrate(taskConfig, this, this)
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone.call()
    }

    TaskConfig getTaskConfig() {
        taskConfig
    }

    private void initLogging() {
        log = LoggerFactory.getLogger(loggerName)
    }

    private String getLoggerName() {
        /wfm.$ctx.workflowName/
    }
}
