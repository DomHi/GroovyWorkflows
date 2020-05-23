package gwf.wfm.executor

import gwf.api.executor.ExecutorConfig
import gwf.api.executor.WorkflowExecutor

class ExecutorConfigImpl implements ExecutorConfig {

    private Class<? extends WorkflowExecutor> executorClass = WorkflowExecutorImpl.class;

    private Map<String, Object> properties = new HashMap<>();

    @Override
    Class<? extends WorkflowExecutor> getExecutorClass() {
        executorClass
    }

    @Override
    <T extends WorkflowExecutor> void setExecutorClass(Class<T> impl) {
        executorClass = impl
    }

    @Override
    Map<String, Object> getProperties() {
        properties
    }
}
