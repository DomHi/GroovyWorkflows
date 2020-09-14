package gwf.wfm.impl.phase;

import gwf.wfm.impl.delegate.AbstractWorkflowDelegate;
import gwf.wfm.impl.executor.TaskExecutorImpl;

import java.util.HashMap;

public class ExecutionPhase {

	private ExecutionPhase() {
	}

	public static void run(AbstractWorkflowDelegate delegate) {
		delegate.run(new TaskExecutorImpl(), new HashMap<>());
	}
}
