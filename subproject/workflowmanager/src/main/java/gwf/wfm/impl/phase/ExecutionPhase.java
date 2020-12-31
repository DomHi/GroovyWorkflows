package gwf.wfm.impl.phase;

import gwf.api.WorkflowManagerException;
import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.executor.TaskExecutorImpl;

import java.util.HashMap;

public class ExecutionPhase {

	private static final ThreadLocal<Object> activePhase = new ThreadLocal<>();

	private ExecutionPhase() {
	}

	public static void run(AbstractWorkflowDelegate delegate) {
		if (ConfigurationPhase.isActive()) {
			throw new WorkflowManagerException("Failed to start execution phase. Configuration phase is active.");
		}

		try {
			activePhase.set(new Object());
			delegate.run(new TaskExecutorImpl(), new HashMap<>());
		} finally {
			activePhase.remove();
		}
	}

	public static boolean isActive() {
		return activePhase.get() != null;
	}
}
