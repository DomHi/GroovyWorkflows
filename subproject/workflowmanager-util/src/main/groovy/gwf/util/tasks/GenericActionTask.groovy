package gwf.util.tasks;

import gwf.api.task.TaskExecutionResult;

/**
 * Task implementation which allows to run generic actions (in the form of Closures) during execution phase.
 */
class GenericActionTask extends AbstractWorkflowTask {

	private final List<Closure<?>> taskActions = new ArrayList<>();

	void action(Closure<?> cl) {
		// TODO use utility class to wrap this functionality
		Closure<?> clone = (Closure<?>) cl.clone();
		clone.setResolveStrategy(Closure.DELEGATE_FIRST);
		taskActions.add(clone);
	}

	@Override
	<T extends TaskExecutionResult> T execute() {

		for(Closure<?> action : taskActions) {
			try {
				action.call();
			} catch (RuntimeException e) {
				handle(e);
			}
		}

		// TODO return meaningful result
		return null;
	}

	// TODO add configuration option to decide how to handle Exceptions
	private void handle(RuntimeException e) {
		throw e;
	}
}
