package gwf.api.task;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Collection;

public interface TaskConfig {

	/**
	 * Add a {@code WorkflowTask} of given type and configure it using the given {@code Closure}.
	 *
	 * @param clazz type of task
	 * @param cl to configure the task
	 */
	<T extends WorkflowTask> void task(
			@DelegatesTo.Target Class<T> clazz,
			@DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl
	);

	<T extends WorkflowTask> Collection<T> getTasks();
}
