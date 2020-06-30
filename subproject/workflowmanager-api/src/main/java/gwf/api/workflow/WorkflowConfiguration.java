package gwf.api.workflow;

import gwf.api.delegate.WorkflowDelegateBase;

import java.net.URI;

public interface WorkflowConfiguration {

	void configure(WorkflowDelegateBase delegate);

	URI getLocation();
}
