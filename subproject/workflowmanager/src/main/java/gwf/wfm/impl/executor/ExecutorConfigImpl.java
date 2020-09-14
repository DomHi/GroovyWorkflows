package gwf.wfm.impl.executor;

import gwf.api.executor.ExecutorConfig;
import gwf.api.executor.TaskExecutor;

import java.util.HashMap;
import java.util.Map;

public class ExecutorConfigImpl implements ExecutorConfig {

	private TaskExecutor executor = null;

	private final Map<String, Object> properties = new HashMap<>();

	@Override
	public TaskExecutor getExecutor() {
		return executor;
	}

	@Override
	public void setExecutor(TaskExecutor executor) {
		this.executor = executor;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}
}
