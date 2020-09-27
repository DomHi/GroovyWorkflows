package gwf.api.task;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.executor.TaskExecutor;
import gwf.api.task.instance.TaskInstantiator;

public interface TaskContainer {

	/**
	 * Set {@code TaskInstantiator} used by this TaskConfig instance.
	 *
	 * @param instantiator to use when creating task instances
	 */
	void setInstantiator(TaskInstantiator instantiator);

	/**
	 * Set {@code TaskExecutor} used by this TaskConfig instance.
	 *
	 * @param executor which will be used to execute tasks
	 */
	void setExecutor(TaskExecutor executor);

	/**
	 * Add a {@code WorkflowTask} of given type and configure it using the given {@code Closure}.
	 *
	 * @param clazz type of task
	 * @param cl    {@code Closure} used to configure the task
	 */
	<T, U extends WorkflowTask<T>> void task(
			Class<U> clazz,
			@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "T") Closure<?> cl
	);

	/**
	 * Add a {@code WorkflowTask} of given type and configure it using the given {@code Closure}.
	 *
	 * @param name  of the new task
	 * @param clazz type of task
	 * @param cl    {@code Closure} used to configure the task
	 */
	<T, U extends WorkflowTask<T>> void task(
			String name,
			Class<U> clazz,
			@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "T") Closure<?> cl
	);
}
