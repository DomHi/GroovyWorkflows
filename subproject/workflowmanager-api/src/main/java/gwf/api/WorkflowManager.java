package gwf.api;

import gwf.api.exception.WorkflowManagerException;
import gwf.api.workflow.WorkflowExecutionContext;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

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

	static WorkflowManager getInstance() {
		try {
			Instance<WorkflowManager> instance = CDI.current().select(WorkflowManager.class);
			if (instance.isUnsatisfied()) {
				throw new WorkflowManagerException("No injectable instance of WorkflowManager found.");
			}
			return instance.get();
		} catch(WorkflowManagerException e) {
			throw e;
		} catch(RuntimeException e) {
			throw new WorkflowManagerException("Failed to obtain instance of WorkflowManager.", e);
		}
	}
}
