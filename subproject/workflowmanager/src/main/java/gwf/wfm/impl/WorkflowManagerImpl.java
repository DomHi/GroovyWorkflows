package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext;
import gwf.api.discovery.WorkflowDiscovery;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.task.WorkflowTask;
import gwf.api.workflow.WorkflowConfiguration;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.delegate.WorkflowDelegateImpl;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.Objects;

public class WorkflowManagerImpl implements WorkflowManager {

	private static final Logger log = LoggerFactory.getLogger(WorkflowManagerImpl.class);

	@Inject
	private Instance<WorkflowDiscovery> discoveries;

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
		WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(wf);

		if (wf != null) {
			wf.configure(delegate);
		} else {
			log.info("workflow was null.");
			return;
		}

		delegate.getTasks().forEach(WorkflowTask::execute);
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
		return discoveries.stream()
				.map(WorkflowDiscovery::find)
				.filter(Objects::nonNull)
				.findFirst()
				.map(WorkflowConfigurationImpl::new)
				.orElse(null);
	}
}
