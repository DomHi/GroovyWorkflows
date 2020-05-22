package gwf.wfm

import gwf.api.workflow.ImmutableWorkflowExecutionContext
import gwf.api.workflow.WorkflowConfiguration
import gwf.api.workflow.WorkflowExecutionContext
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext
import gwf.api.discovery.WorkflowDiscovery
import gwf.api.discovery.WorkflowDiscoveryContext
import gwf.wfm.delegate.WorkflowDelegateImpl
import gwf.wfm.workflow.WorkflowConfigurationImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.inject.Instance
import javax.inject.Inject

class WorkflowManager {

    private static final Logger log = LoggerFactory.getLogger(WorkflowManager)

    @Inject
    private Instance<WorkflowDiscovery> discoveries;

    String getGreeting() {
        /blubb/
    }

    void doSomething() {
        String wfName = "myTest"

        WorkflowExecutionContext ctx = getCtx(wfName)
        WorkflowConfiguration wf = getWorkflow(ctx)

        WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(ctx)

        if(wf != null) {
            wf.configure(delegate)
        } else {
            log.info("workflow was null.")
            return
        }

        delegate.taskConfig.tasks*.execute()
    }

    private WorkflowExecutionContext getCtx(String wfName) {
        ImmutableWorkflowExecutionContext.builder()
            .workflowName(wfName)
            .build()
    }

    private WorkflowConfiguration getWorkflow(WorkflowExecutionContext ctx) {
        WorkflowDiscoveryContext discoveryContext = ImmutableWorkflowDiscoveryContext.builder()
            .name(ctx.workflowName)
            .release(ctx.release)
            .swVersion(ctx.swVersion)
            .technology(ctx.technology)
            .build()

        discoveries.stream()
            .map(d -> d.find discoveryContext)
            .filter(Objects::nonNull)
            .findFirst()
            .map(uri -> new WorkflowConfigurationImpl(uri))
            .orElse(null)
    }
}
