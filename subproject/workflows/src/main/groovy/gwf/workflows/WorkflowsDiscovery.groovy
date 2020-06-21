package gwf.workflows

import gwf.api.discovery.WorkflowDiscovery
import gwf.api.discovery.WorkflowDiscoveryContext
import gwf.api.workflow.context.WorkflowContext

class WorkflowsDiscovery implements WorkflowDiscovery {

    @Override
    URI find() {
        WorkflowDiscoveryContext ctx = WorkflowContext.get(WorkflowDiscoveryContext)
        return this.class.classLoader.getResource(resourceName(ctx))?.toURI()
    }

    private static String resourceName(WorkflowDiscoveryContext ctx) {
        return "workflows/${ctx.name}.wfl"
    }
}
