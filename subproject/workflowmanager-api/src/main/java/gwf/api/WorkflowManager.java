package gwf.api;

import java.util.Map;

/**
 * Implementations of this interface are an entry point for using the worklow API.
 * During runtime an implementation can be obtained using CDI.
 */
public interface WorkflowManager {

	/**
	 * Locate and execute a workflow.
	 *
	 * @param workflowName name of the workflow which is supposed to be executed
	 */
	void execute(String workflowName);

	/**
	 * Locate and execute a workflow.
	 *
	 * @param workflowName name of the workflow which is supposed to be executed
	 * @param env          execution environment
	 */
	void execute(String workflowName, Map<String, String> env);

	static WorkflowBuilder builder() {
		return new WorkflowBuilder();
	}
}
