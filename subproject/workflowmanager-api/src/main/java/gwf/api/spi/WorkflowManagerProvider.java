package gwf.api.spi;

import gwf.api.WorkflowManager;
import gwf.api.workflow.locator.WorkflowLocator;

public interface WorkflowManagerProvider {

    /**
     * Get default WorkflowLocator for this manager.
     * (Optional operation)
     *
     * @return default {@code WorkflowLocator} of this manager if it exists, {@code null} otherwise
     */
    WorkflowLocator getWorkflowLocator();

    WorkflowManager getWorkflowManager(WorkflowLocator locator);
}
