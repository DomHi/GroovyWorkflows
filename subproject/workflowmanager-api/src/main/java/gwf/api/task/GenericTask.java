package gwf.api.task;

import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

public class GenericTask extends WorkflowTask {

	private List<Closure<?>> taskActions = new ArrayList<>();

	public void action(Closure<?> cl) {
		cl.setResolveStrategy(Closure.DELEGATE_FIRST);
		taskActions.add(cl);
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
