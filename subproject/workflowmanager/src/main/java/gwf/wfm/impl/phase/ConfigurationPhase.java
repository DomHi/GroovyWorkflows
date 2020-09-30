package gwf.wfm.impl.phase;

import gwf.api.WorkflowManagerException;

import java.util.Deque;
import java.util.LinkedList;

public class ConfigurationPhase {

	private static final ThreadLocal<ConfigurationPhase> activePhase = new ThreadLocal<>();

	private final Deque<String> deque = new LinkedList<>();
	private boolean exclusive = false;

	private ConfigurationPhase() {
	}

	public static void start() {
		ConfigurationPhase phase = activePhase.get();
		if (phase != null) {
			throw new WorkflowManagerException("Configuration phase already active");
		}
		activePhase.set(new ConfigurationPhase());
	}

	public static void end() {
		activePhase.remove();
	}

	public static void execute(String name, Runnable action) {
		enter(name);
		try {
			action.run();
		} finally {
			leave(name);
		}
	}

	public static void executeExclusive(String name, Runnable action) {
		enterExclusive(name);
		try {
			action.run();
		} finally {
			leave(name);
		}
	}

	public static void enter(String action) {
		ConfigurationPhase phase = get();
		phase.checkEnter();
		if (phase.deque.contains(action)) {
			throw new WorkflowManagerException(String.format("Can not enter %s during %s", action, phase.deque));
		}
		phase.deque.addLast(action);
	}

	public static void enterExclusive(String action) {
		ConfigurationPhase phase = get();
		phase.checkEnter();
		if (!phase.deque.isEmpty()) {
			throw new WorkflowManagerException("Can not get exclusive access during " + phase.deque);
		}
		phase.exclusive = true;
		phase.deque.addLast(action);
	}

	public static void leave(String action) {
		ConfigurationPhase phase = get();
		String removed = phase.deque.removeLast();
		phase.exclusive = false;
		if (!removed.equals(action)) {
			throw new WorkflowManagerException(String.format("Expected %s but got %s", removed, action));
		}
	}

	public static void inline(Runnable action) {
		ConfigurationPhase previous = get();

		// set new phase for inline workflow
		activePhase.set(new ConfigurationPhase());
		action.run();

		// restore previous phase
		activePhase.set(previous);
	}

	private static ConfigurationPhase get() {
		ConfigurationPhase phase = activePhase.get();
		if (phase == null) {
			throw new WorkflowManagerException("Configuration phase not active");
		}
		return phase;
	}

	private void checkEnter() {
		if (exclusive) {
			throw new WorkflowManagerException("Exclusive access active for " + deque.peekLast());
		}
	}
}
