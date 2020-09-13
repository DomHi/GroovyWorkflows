package gwf.wfm.impl.phase;

import gwf.api.WorkflowManagerException;
import gwf.api.executor.ExecutorConfig;
import gwf.api.executor.WorkflowExecutor;
import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.executor.provider.TaskProviderImpl;

public class ExecutionPhase {

	private ExecutionPhase() {
	}

	public static void run(AbstractWorkflowDelegate delegate) {
		ExecutorConfig config = delegate.getExecutorConfig();
		WorkflowExecutor executor = getInstance(config.getExecutorClass());
		executor.execute(new TaskProviderImpl(delegate.getTasks()), config.getProperties());
	}

	private static WorkflowExecutor getInstance(Class<? extends WorkflowExecutor> clazz) {
		try {
			return clazz.getConstructor().newInstance();
		} catch (Exception e) {
			throw new WorkflowManagerException("Failed to create instance of " + clazz, e);
		}
	}
}
