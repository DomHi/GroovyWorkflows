package gwf.api;

import gwf.api.workflow.context.WorkflowContext;

import java.util.*;

public class WorkflowExecution {

    private WorkflowManager manager;

    private String workflowName;

    private Map<String, String> env = new HashMap<>();

    private final List<Contextual> contextuals = new ArrayList<>();

    protected WorkflowExecution() {}

    private WorkflowExecution(WorkflowExecution other) {
        this.manager = other.manager;
        this.workflowName = other.workflowName;
        this.env = new HashMap<>(other.env);
    }

    protected void addContextual(Class<?> clazz, Object impl) {
        contextuals.add(new Contextual(clazz, impl));
    }

    protected void setManager(WorkflowManager manager) {
        this.manager = manager;
    }

    protected void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    protected void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public WorkflowExecution withWorkflowName(String workflowName) {
        WorkflowExecution copy = new WorkflowExecution(this);
        copy.workflowName = workflowName;
        return copy;
    }

    public WorkflowExecution withEnv(Map<String, String> env) {
        WorkflowExecution copy = new WorkflowExecution(this);
        copy.env = env;
        return copy;
    }

    public WorkflowExecution withContext(Map<Class<?>, Object> mappedContext, Object... unmappedContext) {
        WorkflowExecution copy = new WorkflowExecution(this);
        copy.contextuals.clear();
        mappedContext.forEach(
                copy::addContextual
        );
        Arrays.stream(unmappedContext).forEach(
                impl -> copy.addContextual(null, impl)
        );
        return copy;
    }

    public WorkflowExecution withContext(Object... unmappedContext) {
        WorkflowExecution copy = new WorkflowExecution(this);
        copy.contextuals.clear();
        Arrays.stream(unmappedContext).forEach(
                impl -> copy.addContextual(null, impl)
        );
        return this;
    }

    public void execute() {
        setContext();
        manager.execute(workflowName, env);
    }

    private void setContext() {
        contextuals.forEach(
                c -> {
                    if (c.getType().isPresent()) {
                        WorkflowContext.add(c.getType().get(), c.getImpl());
                    } else {
                        WorkflowContext.add(c.getImpl());
                    }
                });
    }

    private static class Contextual {

        private final Class<?> clazz;
        private final Object impl;

        private Contextual(Class<?> clazz, Object impl) {
            this.clazz = clazz;
            this.impl = impl;
        }

        private Optional<Class<?>> getType() {
            return Optional.ofNullable(clazz);
        }

        private Object getImpl() {
            return impl;
        }
    }
}
