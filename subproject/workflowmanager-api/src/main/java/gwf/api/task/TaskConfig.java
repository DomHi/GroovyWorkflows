package gwf.api.task;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.task.instance.TaskInstantiator;

import java.util.Collection;

public interface TaskConfig {

	/**
	 * Set {@code TaskInstantiator} used by this TaskConfig instance.
	 *
	 * @param instantiator to use when creating task instances
	 */
	void setTaskInstantiator(TaskInstantiator instantiator);

	/**
	 * @return {@code TaskInstantiator} used to create task instances
	 */
	TaskInstantiator getTaskInstantiator();

	/**
	 * Add a {@code WorkflowTask} of given type and configure it using the given {@code Closure}.
	 *
	 * @param clazz type of task
	 * @param cl {@code Closure} used to configure the task
	 */
	<T extends WorkflowTask> void task(
			@DelegatesTo.Target Class<T> clazz,
			@DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl
	);

	/**
	 * Add a {@code WorkflowTask} of given type and configure it using the given {@code Closure}.
	 *
	 * @param name of the new task
	 * @param clazz type of task
	 * @param cl {@code Closure} used to configure the task
	 */
	<T extends WorkflowTask> void task(
			String name,
			@DelegatesTo.Target Class<T> clazz,
			@DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl
	);

	Collection<WorkflowTask> getTasks();
}
