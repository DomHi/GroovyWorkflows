package gwf.api;

import gwf.api.spi.WorkflowManagerProvider;
import gwf.api.workflow.context.WorkflowContext;
import gwf.api.workflow.locator.WorkflowLocator;
import lombok.*;

import java.util.*;

@Getter
@Builder(toBuilder = true)
public class WorkflowExecution {

    @With
    @NonNull
    private final String workflowName;

    private final WorkflowLocator locator;

    private final WorkflowManager manager;

    private final Map<String, String> env;

    @Singular private final List<Contextual> contextuals;

    public void execute() {
        setContext();
        lookupManager().execute(workflowName, lookupEnv());
    }

    private void setContext() {
        WorkflowContext.clear();
        contextuals.forEach(
                c -> {
                    if (c.getType() != null) {
                        WorkflowContext.add(c.getType(), c.getImpl());
                    } else {
                        WorkflowContext.add(c.getImpl());
                    }
                });
    }

    private WorkflowManager lookupManager() {

        if (manager != null) {
            return manager;
        }

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

        WorkflowLocator currentLocator = locator;
        if (currentLocator == null) {
            currentLocator = provider.getWorkflowLocator();
        }

        return provider.getWorkflowManager(currentLocator);
    }

    private Map<String, String> lookupEnv() {
        return getEnv() == null ? new HashMap<>() : getEnv();
    }

    // add convenience methods to builder generated by Lombok
    public static class WorkflowExecutionBuilder {

        public WorkflowExecutionBuilder addContextual(Class<?> type, Object impl) {
            return contextual(Contextual.of(type, impl));
        }

        public WorkflowExecutionBuilder addContextual(Object impl) {
            return contextual(Contextual.of(impl));
        }
    }
}
