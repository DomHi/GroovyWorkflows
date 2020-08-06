package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.delegate.Delegation;
import gwf.wfm.impl.executor.ExecutionPhase;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.wfm.impl.workflow.WorkflowLocator;

import javax.inject.Inject;

public class WorkflowManagerImpl implements WorkflowManager {

	@Inject
	private WorkflowLocator locator;

	@Override
	public void execute(String workflowName) {

		setCtx(workflowName);

		try {
			executeInternal();
		} finally {
			WorkflowContext.clear();
		}
	}

	private void executeInternal() {
		WorkflowConfiguration wf = getWorkflow();
		AbstractWorkflowDelegate delegate = Delegation.initial(wf);
		ExecutionPhase.run(delegate);
	}

	private void setCtx(String wfName) {
		WorkflowContext.add(
				WorkflowDiscoveryContext.class,
				ImmutableWorkflowDiscoveryContext.builder()
						.name(wfName)
						.build()
		);
	}

	private WorkflowConfiguration getWorkflow() {
		return locator.discover()
				.map(WorkflowConfigurationImpl::new)
				.orElse(null);
	}
}
