package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.executor.ExecutorConfig;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.executor.ExecutorConfigImpl;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowDelegateImpl extends AbstractWorkflowDelegate {

	private Logger log;

	private final ExecutorConfig executorConfig = new ExecutorConfigImpl();

	public WorkflowDelegateImpl(WorkflowConfiguration config) {
		super(config);
		initLogging();
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public void executor(Closure<?> cl) {
		ClosureUtil.delegateFirst(cl, executorConfig).call();
	}

	@Override
	public ExecutorConfig getExecutorConfig() {
		return executorConfig;
	}

	private void initLogging() {
		log = LoggerFactory.getLogger(getLoggerName());
	}

	private String getLoggerName() {
		WorkflowDiscoveryContext ctx = WorkflowContext.get(WorkflowDiscoveryContext.class);
		return String.format("wfm.%s", ctx.getName());
	}
}
