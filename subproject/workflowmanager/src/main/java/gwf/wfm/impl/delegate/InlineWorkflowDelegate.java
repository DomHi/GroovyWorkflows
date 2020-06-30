package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.delegate.WorkflowDelegateBase;
import org.slf4j.Logger;

public class InlineWorkflowDelegate implements WorkflowDelegateBase {

	private final Logger logger;

	protected InlineWorkflowDelegate(Logger logger) {
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
	public void tasks(Closure<?> config) {
		// TODO implement
	}

	@Override
	public void inline(String path) {
		// TODO implement
	}
}