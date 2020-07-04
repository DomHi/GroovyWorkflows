package gwf.wfm.impl.delegate;

import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.WorkflowTask;

import java.util.Collection;

public interface InternalWorkflowDelegate extends WorkflowDelegateBase {

	Collection<WorkflowTask> getTasks();

	ExecutorConfig getExecutorConfig();
}
