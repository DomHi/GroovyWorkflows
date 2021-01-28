package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.WorkflowManagerException;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.delegate.Delegation;
import gwf.wfm.impl.phase.ExecutionPhase;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.api.workflow.locator.WorkflowLocator;

import java.util.HashMap;
import java.util.Map;

public class WorkflowManagerImpl implements WorkflowManager {

	private final WorkflowLocator locator;

	WorkflowManagerImpl(WorkflowLocator locator) {
		this.locator = locator;
	}

	@Override
	public void execute(String workflowName) {
		execute(workflowName, new HashMap<>());
	}

	@Override
	public void execute(String workflow, Map<String, String> env) {

		setCtx();

		try {
			executeInternal(workflow, new HashMap<>(env));
		} finally {
			WorkflowContext.clear();
		}
	}

	private void executeInternal(String workflow, Map<String, String> env) {
		WorkflowConfiguration wf = getWorkflow(workflow, env);
		AbstractWorkflowDelegate delegate = Delegation.initial(wf);
		ExecutionPhase.run(delegate);
	}

	private void setCtx() {
		WorkflowContext.add(
				WorkflowLocator.class,
				locator
		);
	}

	private WorkflowConfiguration getWorkflow(String workflow, Map<String, String> env) {
		return locator.absolute(workflow)
				.map(uri -> new WorkflowConfigurationImpl(uri, env))
				.orElseThrow(() -> new WorkflowManagerException("Invalid workflow: " + workflow));
	}
}
