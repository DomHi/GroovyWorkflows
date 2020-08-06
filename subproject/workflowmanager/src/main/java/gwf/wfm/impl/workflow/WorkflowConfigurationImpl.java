package gwf.wfm.impl.workflow;

import gwf.api.delegate.WorkflowDelegateBase;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WorkflowConfigurationImpl implements WorkflowConfiguration {

	private final URI location;
	private final WorkflowScript script;
	private final Map<String, String> env;

	public WorkflowConfigurationImpl(URI source) {
		this(source, new HashMap<>());
	}

	public WorkflowConfigurationImpl(URI source, Map<String, String> env) {
		this.location = source;
		this.env = env;
		this.script = ScriptLoader.load(source);
	}

	@Override
	public void configure(WorkflowDelegateBase delegate) {
		script.setDelegate(delegate);
		script.run();
	}

	@Override
	public URI getLocation() {
		return location;
	}

	@Override
	public Map<String, String> getEnv() {
		return env;
	}
}
