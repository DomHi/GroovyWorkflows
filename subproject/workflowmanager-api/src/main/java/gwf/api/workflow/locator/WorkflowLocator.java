package gwf.api.workflow.locator;

import java.net.URI;
import java.util.Optional;

public interface WorkflowLocator {

    Optional<URI> absolute(String path);

    URI relative(URI current, String path);
}
