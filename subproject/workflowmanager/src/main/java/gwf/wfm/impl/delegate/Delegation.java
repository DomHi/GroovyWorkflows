package gwf.wfm.impl.delegate;

import gwf.api.workflow.WorkflowConfiguration;

public class Delegation {

    public static WorkflowDelegateImpl configuredBy(WorkflowConfiguration cfg) {
        WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(cfg);
        cfg.configure(delegate);
        return delegate;
    }
}
