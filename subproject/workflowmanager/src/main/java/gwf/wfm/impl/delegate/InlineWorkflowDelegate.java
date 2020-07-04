package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.WorkflowTask;
import org.slf4j.Logger;

import java.util.Collection;

public class InlineWorkflowDelegate implements InternalWorkflowDelegate {

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

	@Override
	public Collection<WorkflowTask> getTasks() {
		return null;
	}

	@Override
	public ExecutorConfig getExecutorConfig() {
		return null;
	}
}
