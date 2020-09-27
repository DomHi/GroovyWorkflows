package gwf.util.task.generic;

import groovy.lang.Closure;
import gwf.api.task.TaskExecutionResult;
import gwf.util.task.AbstractWorkflowTask;

import java.util.function.Consumer;

public class GenericAction extends AbstractWorkflowTask<GenericConfig> {

	private GenericConfigImpl config = null;

	@Override
	public void configure(Consumer<GenericConfig> c) {
		this.config = new GenericConfigImpl(getName());
		c.accept(config);
	}

	@Override
	public TaskExecutionResult execute() {

		for (Closure<?> action : config.getTaskActions()) {
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
