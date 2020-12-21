package gwf.wfm.impl;

import gwf.api.WorkflowManager;
import gwf.api.spi.WorkflowManagerProvider;
import gwf.api.workflow.locator.WorkflowLocator;

public class WorkflowManagerProviderImpl implements WorkflowManagerProvider {

    @Override
    public WorkflowLocator getWorkflowLocator() {
        // there is no default yet
        return null;
    }

    @Override
    public WorkflowManager getWorkflowManager(WorkflowLocator locator) {
        return new WorkflowManagerImpl(locator);
    }
}
