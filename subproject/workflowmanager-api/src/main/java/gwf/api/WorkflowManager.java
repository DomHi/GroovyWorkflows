package gwf.api;

import gwf.api.workflow.WorkflowExecutionContext;

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
	 * @param ctx {@code WorkflowExecutionContext} which will be used to locate and initialize the workflow
	 */
	void execute(WorkflowExecutionContext ctx);
}
