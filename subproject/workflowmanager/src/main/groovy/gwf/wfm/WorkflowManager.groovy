package gwf.wfm

import gwf.api.workflow.WorkflowExecutionContext
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext
import gwf.api.discovery.WorkflowDiscovery
import gwf.api.discovery.WorkflowDiscoveryContext
import gwf.wfm.delegate.WorkflowDelegateImpl
import gwf.wfm.workflow.Workflow
import gwf.wfm.workflow.WorkflowExecutionContextImpl
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.ejb.Stateless
import javax.enterprise.inject.Instance
import javax.inject.Inject

@Stateless
class WorkflowManager {

    private static final Logger log = LoggerFactory.getLogger(WorkflowManager)

    @Inject
    private Instance<WorkflowDiscovery> discoveries;

    String getGreeting() {
        /blubb/
    }

    void doSomething() {
        String wfName = "myTest"

        Workflow wf = getWorkflow(wfName)
        WorkflowExecutionContext ctx = getCtx(wfName)

        WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(ctx)

        if(wf != null) {
            wf.run(delegate)
        } else {
            log.info("workflow was null.")
            return
        }

        delegate.taskConfig.tasks*.execute()
    }

    private WorkflowExecutionContext getCtx(String wfName) {
        new WorkflowExecutionContextImpl(wfName)
    }

    private Workflow getWorkflow(String name) {
        WorkflowDiscoveryContext ctx = ImmutableWorkflowDiscoveryContext.builder()
            .name(name)
            .build()

        URI source = null
        for(WorkflowDiscovery d : discoveries) {
            log.info "try discovery: $d.class.name"
            source = d.find ctx
        }

        return source != null ? new Workflow(source) : null
    }
}
