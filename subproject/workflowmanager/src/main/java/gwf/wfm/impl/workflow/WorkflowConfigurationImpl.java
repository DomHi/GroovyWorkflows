package gwf.wfm.impl.workflow;

import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.workflow.WorkflowConfiguration;

import java.net.URI;

public class WorkflowConfigurationImpl implements WorkflowConfiguration {

    private final URI location;
    private final WorkflowScript script;

    public WorkflowConfigurationImpl(URI source) {
        location = source;
        script = ScriptLoader.load(source);
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
}
