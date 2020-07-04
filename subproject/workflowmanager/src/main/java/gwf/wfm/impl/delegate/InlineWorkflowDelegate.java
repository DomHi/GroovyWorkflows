package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.executor.ExecutorConfig;
import gwf.api.workflow.WorkflowConfiguration;
import org.slf4j.Logger;

public class InlineWorkflowDelegate extends AbstractWorkflowDelegate {

	private final Logger logger;

	protected InlineWorkflowDelegate(Logger logger, WorkflowConfiguration cfg) {
		super(cfg);
		this.logger = logger;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void executor(Closure<?> cl) {
		throw new UnsupportedOperationException("Modifying executor config not supported in inline workflows");
	}

	@Override
	public void inline(String path) {
		// TODO implement
	}

	@Override
	public ExecutorConfig getExecutorConfig() {
		return null;
	}
}
