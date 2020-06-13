package gwf.wfm.task

import gwf.api.task.TaskConfig
import gwf.api.task.WorkflowTask
import gwf.api.task.instance.TaskInstantiator

class TaskConfigImpl implements TaskConfig {

    private TaskInstantiator instantiator

    private List<WorkflowTask> tasks = new ArrayList<>()

    TaskConfigImpl(TaskInstantiator instantiator) {
        this.instantiator = instantiator
    }

    @Override
    void setTaskInstantiator(TaskInstantiator instantiator) {
        this.instantiator = instantiator
    }

    @Override
    TaskInstantiator getTaskInstantiator() {
        instantiator
    }

    @Override
    <T extends WorkflowTask> void task(
            @DelegatesTo.Target Class<T> clazz,
            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl) {

        def impl = instantiator.create(clazz)

        // TODO implement utilty class to handle delegation
        def clone = (Closure) cl.clone()
        clone.setDelegate(impl)
        clone.call()

        tasks.add(impl)
    }

    @Override
    <T extends WorkflowTask> void task(
            String name,
            @DelegatesTo.Target Class<T> clazz,
            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl) {

        // TODO implement
    }

    @Override
    Collection<WorkflowTask> getTasks() {
        tasks
    }
}
