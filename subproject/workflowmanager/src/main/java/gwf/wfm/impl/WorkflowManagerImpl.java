package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.delegate.Delegation;
import gwf.wfm.impl.phase.ExecutionPhase;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.wfm.impl.workflow.locator.WorkflowLocator;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class WorkflowManagerImpl implements WorkflowManager {

	private final WorkflowLocator locator;

	@Inject
	WorkflowManagerImpl(WorkflowLocator locator) {
		this.locator = locator;
	}

	@Override
	public void execute(String workflowName) {
		execute(workflowName, new HashMap<>());
	}

	@Override
	public void execute(String workflowName, Map<String, String> env) {

		setCtx(workflowName);

		try {
			executeInternal(new HashMap<>(env));
		} finally {
			WorkflowContext.clear();
		}
	}

	private void executeInternal(Map<String, String> env) {
		WorkflowConfiguration wf = getWorkflow(env);
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

	private WorkflowConfiguration getWorkflow(Map<String, String> env) {
		return locator.discover()
				.map(uri -> new WorkflowConfigurationImpl(uri, env))
				.orElse(null);
	}
}
