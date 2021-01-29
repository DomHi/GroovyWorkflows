package gwf.workflows;

import gwf.api.workflow.locator.WorkflowLocator;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

public class WorkflowsLocatorImpl implements WorkflowLocator {

    private URI find(String path) {
        URL resource = this.getClass().getClassLoader().getResource(resourceName(path));
        try {
            return resource == null ? null : resource.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String resourceName(String path) {
        return "workflows/" + path + ".wfl";
    }

    @Override
    public Optional<URI> absolute(String path) {
        return Optional.ofNullable(find(path));
    }

    @Override
    public URI relative(URI current, String path) {
        return current.resolve(path);
    }
}
