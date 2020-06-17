package gwf.wfm.impl.executor;

import gwf.api.executor.ExecutorConfig;
import gwf.api.executor.WorkflowExecutor;

import java.util.HashMap;
import java.util.Map;

public class ExecutorConfigImpl implements ExecutorConfig {

	private Class<? extends WorkflowExecutor> executorClass = WorkflowExecutorImpl.class;

	private final Map<String, Object> properties = new HashMap<>();

	@Override
	public Class<? extends WorkflowExecutor> getExecutorClass() {
		return executorClass;
	}

	@Override
	public <T extends WorkflowExecutor> void setExecutorClass(Class<T> executorClass) {
		this.executorClass = executorClass;
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}
}
