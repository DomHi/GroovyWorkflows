package gwf.wfm.impl.delegate;

import gwf.api.workflow.WorkflowConfiguration;

public class Delegation {

	private Delegation() {
	}

	public static InternalWorkflowDelegate configuredBy(WorkflowConfiguration cfg) {
		InternalWorkflowDelegate delegate = new WorkflowDelegateImpl(cfg);
		cfg.configure(delegate);
		return delegate;
	}
}
