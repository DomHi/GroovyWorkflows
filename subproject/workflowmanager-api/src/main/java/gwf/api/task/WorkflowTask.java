package gwf.api.task;

/**
 * Base interface for all worklow tasks.
 */
public interface WorkflowTask {

	String getName();

	void setName(String name);

	<T extends TaskExecutionResult> T execute();
}
