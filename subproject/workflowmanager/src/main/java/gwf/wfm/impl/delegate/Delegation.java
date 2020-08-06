package gwf.wfm.impl.delegate;

import gwf.wfm.impl.workflow.WorkflowConfiguration;

public class Delegation {

	private Delegation() {
	}

	public static AbstractWorkflowDelegate initial(WorkflowConfiguration cfg) {
		AbstractWorkflowDelegate delegate = new WorkflowDelegateImpl(cfg);
		cfg.configure(delegate);
		return delegate;
	}

	public static AbstractWorkflowDelegate inlined(AbstractWorkflowDelegate parent, WorkflowConfiguration cfg) {
		AbstractWorkflowDelegate delegate = new InlineWorkflowDelegate(parent.getLogger(), cfg);
		cfg.configure(delegate);
		return delegate;
	}
}
