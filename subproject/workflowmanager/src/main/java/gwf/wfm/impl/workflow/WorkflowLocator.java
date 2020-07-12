package gwf.wfm.impl.workflow;

import gwf.api.discovery.WorkflowDiscovery;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.net.URI;
import java.util.Optional;

public class WorkflowLocator {

	@Inject
	private Instance<WorkflowDiscovery> discoveries;

	public Optional<URI> discover() {
		for (WorkflowDiscovery d : discoveries) {
			URI wf = d.find();
			if (wf != null) {
				return Optional.of(wf);
			}
		}
		return Optional.empty();
	}

	public URI relative(URI current, String path) {
		return current.resolve(path);
	}
}
