package gwf.api.executor;

import java.util.Map;

public interface ExecutorConfig {

	/**
	 * @return currently configured executor class
	 */
	Class<? extends  ExecutorConfig> getExecutorClass();

	/**
	 * Provide executor class which should be used to execute tasks
	 *
	 * @param impl executor class
	 */
	<T extends  ExecutorConfig> void setExecutorClass(Class<T> impl);

	/**
	 * @return properties configured for this configuration
	 */
	Map<String, Object> getProperties();
}
