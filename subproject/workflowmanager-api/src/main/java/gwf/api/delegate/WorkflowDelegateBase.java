package gwf.api.delegate;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskConfig;
import org.slf4j.Logger;

/**
 * Base interface implemented by delegates of workflow scripts.
 * Provides utilities for logging, obtaining workflow context and adding tasks.
 */
public interface WorkflowDelegateBase {

	/**
	 * @return Logger for the current workflow context
	 */
	Logger getLogger();

	/**
	 * @param cl Closure used to configure executors
	 */
	void executor(@DelegatesTo(value = ExecutorConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> cl);

	/**
	 * Configures the TaskConfig using the given Closure.
	 *
	 * @param config Closure which will be executed with the TaskConfig delegate
	 */
	void tasks(@DelegatesTo(value = TaskConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> config);
}
