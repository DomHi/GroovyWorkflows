package gwf.wfm.impl.workflow;

import gwf.api.discovery.WorkflowDiscovery;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

public class WorkflowLocator {

    @Inject
    private Instance<WorkflowDiscovery> discoveries;

    public Optional<URI> discover() {
        return discoveries.stream()
                .map(WorkflowDiscovery::find)
                .filter(Objects::nonNull)
                .findFirst();
    }

    public URI relative(URI current, String path) {
        return current.resolve(path);
    }
}
