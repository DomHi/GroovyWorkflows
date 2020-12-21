package gwf.api;

import gwf.api.spi.WorkflowManagerProvider;
import gwf.api.workflow.locator.WorkflowLocator;

import java.util.*;

public class WorkflowBuilder {

    private WorkflowLocator locator = null;

    private String workflowName = null;
    private Map<String, String> env = new HashMap<>();

    private final List<Object> contextualObjects = new ArrayList<>();
    private final Map<Class<?>, Object> mappedContext = new HashMap<>();

    public static WorkflowBuilder create() {
        return new WorkflowBuilder();
    }

    protected WorkflowBuilder() {}

    public WorkflowBuilder workflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    public WorkflowBuilder env(Map<String, String> env) {
        this.env = env;
        return this;
    }

    public WorkflowBuilder locator(WorkflowLocator locator) {
        this.locator = locator;
        return this;
    }

    public <T> WorkflowBuilder context(Class<T> clazz, T instance) {
        mappedContext.put(clazz, instance);
        return this;
    }

    public WorkflowBuilder context(Object instance) {
        contextualObjects.add(instance);
        return this;
    }

    private WorkflowManager getManager() {

        ServiceLoader<WorkflowManagerProvider> loader = ServiceLoader.load(WorkflowManagerProvider.class);

        WorkflowManagerProvider provider = null;
        for(WorkflowManagerProvider p : loader) {
            if (provider != null) {
                throw new WorkflowManagerException("Ambiguous WorkflowManagerProvider");
            }
            provider = p;
        }

        if (provider == null) {
            throw new WorkflowManagerException("No WorkflowManagerProvider found");
        }

        if (locator == null) {
            locator = provider.getWorkflowLocator();
        }

        return provider.getWorkflowManager(locator);
    }

    public WorkflowExecution build() {
        WorkflowExecution execution = new WorkflowExecution();

        execution.setEnv(env);
        execution.setManager(getManager());
        execution.setWorkflowName(workflowName);

        contextualObjects.forEach(
                obj -> execution.addContextual(null, obj)
        );

        mappedContext.forEach(
                execution::addContextual
        );

        return execution;
    }
}
