package gwf.wfm.impl.executor;

import gwf.api.WorkflowManagerException;
import gwf.api.executor.ExecutorConfig;
import gwf.api.executor.WorkflowExecutor;
import gwf.wfm.impl.delegate.WorkflowDelegateImpl;

public class ExecutionPhase {

    public static void run(WorkflowDelegateImpl delegate) {
        ExecutorConfig config = delegate.getExecutorConfig();
        WorkflowExecutor executor = getInstance(config.getExecutorClass());
        executor.execute(delegate.getTasks());
    }

    private static WorkflowExecutor getInstance(Class<? extends WorkflowExecutor> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new WorkflowManagerException("Failed to create instance of " + clazz, e);
        }
    }
}
