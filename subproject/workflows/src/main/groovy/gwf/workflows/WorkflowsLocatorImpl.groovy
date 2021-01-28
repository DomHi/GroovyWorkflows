package gwf.workflows

import gwf.api.discovery.WorkflowDiscoveryContext
import gwf.api.workflow.context.WorkflowContext
import gwf.api.workflow.locator.WorkflowLocator

class WorkflowsLocatorImpl implements WorkflowLocator {

    private URI find() {
        WorkflowDiscoveryContext ctx = WorkflowContext.get(WorkflowDiscoveryContext)
        return this.class.classLoader.getResource(resourceName(ctx))?.toURI()
    }

    private static String resourceName(WorkflowDiscoveryContext ctx) {
        return "workflows/${ctx.name}.wfl"
    }

    @Override
    Optional<URI> discover() {
        return Optional.ofNullable(find())
    }

    @Override
    URI relative(URI current, String path) {
        return current.resolve(path)
    }
}
