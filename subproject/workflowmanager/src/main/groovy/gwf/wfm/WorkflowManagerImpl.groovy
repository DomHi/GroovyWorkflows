package gwf.wfm

import gwf.api.WorkflowManager
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

class WorkflowManagerImpl implements WorkflowManager {

    private static final Logger log = LoggerFactory.getLogger(WorkflowManagerImpl)

    @Inject
    private Instance<WorkflowDiscovery> discoveries;

    @Override
    void execute(String workflowName) {
        execute(getCtx(workflowName))
    }

    @Override
    void execute(WorkflowExecutionContext ctx) {

        WorkflowConfiguration wf = getWorkflow(ctx)
        WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(ctx)

        if(wf != null) {
            wf.configure(delegate)
        } else {
            log.info("workflow was null.")
            return
        }

        delegate.tasks*.execute()
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
