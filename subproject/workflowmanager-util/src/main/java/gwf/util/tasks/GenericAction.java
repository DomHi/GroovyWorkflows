package gwf.util.tasks;

import groovy.lang.Closure;
import gwf.api.task.TaskExecutionResult;
import gwf.api.util.ClosureUtil;

import java.util.ArrayList;
import java.util.List;

public class GenericAction extends AbstractWorkflowTask {

	private final List<Closure<?>> taskActions = new ArrayList<>();

	void action(Closure<?> cl) {
		taskActions.add(ClosureUtil.delegateFirst(cl));
	}

	@Override
	public TaskExecutionResult execute() {

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
