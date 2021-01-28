package gwf.workflows

import gwf.api.workflow.locator.WorkflowLocator

class WorkflowsLocatorImpl implements WorkflowLocator {

    private URI find(String path) {
        return this.class.classLoader.getResource(resourceName(path))?.toURI()
    }

    private static String resourceName(String path) {
        return "workflows/${path}.wfl"
    }

    @Override
    Optional<URI> absolute(String path) {
        return Optional.ofNullable(find(path))
    }

    @Override
    URI relative(URI current, String path) {
        return current.resolve(path)
    }
}
