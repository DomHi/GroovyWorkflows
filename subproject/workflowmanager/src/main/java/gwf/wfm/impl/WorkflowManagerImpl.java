package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.discovery.ImmutableWorkflowDiscoveryContext;
import gwf.api.discovery.WorkflowDiscovery;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.task.WorkflowTask;
import gwf.api.workflow.ImmutableWorkflowExecutionContext;
import gwf.api.workflow.WorkflowConfiguration;
import gwf.api.workflow.WorkflowExecutionContext;
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
		execute(getCtx(workflowName));
	}

	@Override
	public void execute(WorkflowExecutionContext ctx) {
		WorkflowConfiguration wf = getWorkflow(ctx);
		WorkflowDelegateImpl delegate = new WorkflowDelegateImpl(ctx);

		if(wf != null) {
			wf.configure(delegate);
		} else {
			log.info("workflow was null.");
			return;
		}

		delegate.getTasks().forEach(WorkflowTask::execute);
	}

	private WorkflowExecutionContext getCtx(String wfName) {
		return ImmutableWorkflowExecutionContext.builder()
				.workflowName(wfName)
				.build();
	}

	private WorkflowConfiguration getWorkflow(WorkflowExecutionContext ctx) {
		WorkflowDiscoveryContext discoveryContext = ImmutableWorkflowDiscoveryContext.builder()
				.name(ctx.getWorkflowName())
				.release(ctx.getRelease())
				.swVersion(ctx.getSwVersion())
				.technology(ctx.getTechnology())
				.build();

		return discoveries.stream()
				.map(d -> d.find(discoveryContext))
				.filter(Objects::nonNull)
				.findFirst()
				.map(WorkflowConfigurationImpl::new)
				.orElse(null);
	}
}
