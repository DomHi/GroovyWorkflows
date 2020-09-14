package gwf.api.executor;

import gwf.api.executor.provider.TaskProvider;

import java.util.Map;

public interface TaskExecutor {

	void execute(TaskProvider taskProvider, Map<String, Object> properties);
}
