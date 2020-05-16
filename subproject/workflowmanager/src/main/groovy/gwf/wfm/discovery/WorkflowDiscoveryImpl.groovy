package gwf.wfm.discovery

import gwf.api.discovery.WorkflowDiscovery
import gwf.api.discovery.WorkflowDiscoveryContext

class WorkflowDiscoveryImpl implements WorkflowDiscovery {

    @Override
    URI find(WorkflowDiscoveryContext ctx) {
        return this.class.classLoader.getResource(resourceName(ctx))?.toURI()
    }

    private static String resourceName(WorkflowDiscoveryContext ctx) {
        return "workflows/${ctx.name}.wfl"
    }
}
