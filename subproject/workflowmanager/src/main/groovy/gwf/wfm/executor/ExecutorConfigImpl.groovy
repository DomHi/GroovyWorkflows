package gwf.wfm.executor

import gwf.api.executor.ExecutorConfig

class ExecutorConfigImpl implements ExecutorConfig {

    private Class<? extends ExecutorConfig> executorClass;

    private Map<String, Object> properties = new HashMap<>();

    @Override
    Class<? extends ExecutorConfig> getExecutorClass() {
        executorClass
    }

    @Override
    <T extends ExecutorConfig> void setExecutorClass(Class<T> impl) {
        executorClass = impl
    }

    @Override
    Map<String, Object> getProperties() {
        properties
    }
}
