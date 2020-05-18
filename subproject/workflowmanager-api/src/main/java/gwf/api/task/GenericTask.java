package gwf.api.task;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class GenericTask extends WorkflowTask {

	private final List<Closure<?>> taskActions = new ArrayList<>();

	public void action(Closure<?> cl) {
		// TODO use utility class to wrap this functionality
		Closure<?> clone = (Closure<?>) cl.clone();
		clone.setResolveStrategy(Closure.DELEGATE_FIRST);
		taskActions.add(clone);
	}

	@Override
	public <T extends TaskExecutionResult> T execute() {

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

	private void handle(RuntimeException e) {
		throw e;
	}
}
