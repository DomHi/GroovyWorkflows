package gwf.wfm.impl.workflow.locator;

import java.net.URI;
import java.util.Optional;

public interface WorkflowLocator {

    Optional<URI> discover();

    URI relative(URI current, String path);
}
