package gwf.wfm.impl.task;

import gwf.api.executor.TaskExecutor;

import java.util.Map;

public interface ExecutableTasks {

	void run(TaskExecutor executor, Map<String, Object> properties);
}
