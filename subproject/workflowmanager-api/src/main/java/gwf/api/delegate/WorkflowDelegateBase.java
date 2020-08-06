package gwf.api.delegate;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskContainer;
import org.slf4j.Logger;

import java.util.Map;

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
	 * @return environment of workflow execution
	 */
	Map<String, String> getEnv();

	/**
	 * @param cl Closure used to configure executors
	 */
	void executor(@DelegatesTo(value = ExecutorConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> cl);

	/**
	 * Configures the TaskConfig using the given Closure.
	 *
	 * @param config Closure which will be executed with the TaskConfig delegate
	 */
	void tasks(@DelegatesTo(value = TaskContainer.class, strategy = Closure.DELEGATE_FIRST) Closure<?> config);

	/**
	 * Inline workflow. This will add tasks of given workflow to the current execution.
	 *
	 * @param path of the workflow to inline. Will be resolved relative to current workflows location.
	 */
	void inline(String path);
}
