package gwf.api.executor;

import java.util.Map;

public interface ExecutorConfig {

	/**
	 * @return currently configured executor
	 */
	TaskExecutor getExecutor();

	/**
	 * Provide executor which should be used to execute tasks
	 *
	 * @param executor implementation
	 */
	void setExecutor(TaskExecutor executor);

	/**
	 * @return properties configured for this configuration
	 */
	Map<String, Object> getProperties();
}
