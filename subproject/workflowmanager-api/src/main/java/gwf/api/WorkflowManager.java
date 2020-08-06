package gwf.api;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
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

	static WorkflowManager getInstance() {
		try {
			Instance<WorkflowManager> instance = CDI.current().select(WorkflowManager.class);
			if (instance.isUnsatisfied()) {
				throw new WorkflowManagerException("No injectable instance of WorkflowManager found.");
			}
			return instance.get();
		} catch (WorkflowManagerException e) {
			throw e;
		} catch (RuntimeException e) {
			throw new WorkflowManagerException("Failed to obtain instance of WorkflowManager.", e);
		}
	}
}
