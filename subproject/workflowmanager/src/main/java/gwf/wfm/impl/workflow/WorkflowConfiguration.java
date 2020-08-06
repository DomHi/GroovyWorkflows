package gwf.wfm.impl.workflow;

import gwf.api.delegate.WorkflowDelegateBase;

import java.net.URI;
import java.util.Map;

public interface WorkflowConfiguration {

	void configure(WorkflowDelegateBase delegate);

	URI getLocation();

	Map<String, String> getEnv();
}
