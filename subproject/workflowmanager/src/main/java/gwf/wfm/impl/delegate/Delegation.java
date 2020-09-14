package gwf.wfm.impl.delegate;

import gwf.wfm.impl.phase.ConfigurationPhase;
import gwf.wfm.impl.workflow.WorkflowConfiguration;

public class Delegation {

	private Delegation() {
	}

	public static AbstractWorkflowDelegate initial(WorkflowConfiguration cfg) {
		AbstractWorkflowDelegate delegate = new WorkflowDelegateImpl(cfg);

		ConfigurationPhase.start();
		try {
			cfg.configure(delegate);
		} finally {
			ConfigurationPhase.end();
		}

		return delegate;
	}

	public static AbstractWorkflowDelegate inlined(AbstractWorkflowDelegate parent, WorkflowConfiguration cfg) {
		AbstractWorkflowDelegate delegate = new InlineWorkflowDelegate(parent.getLogger(), parent.getExecutorConfig(), cfg);
		cfg.configure(delegate);
		return delegate;
	}
}
