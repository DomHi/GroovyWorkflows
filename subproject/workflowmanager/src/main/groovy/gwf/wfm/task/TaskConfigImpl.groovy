package gwf.wfm.task

import gwf.api.task.TaskConfig
import gwf.api.task.WorkflowTask
import gwf.api.task.instance.TaskInstantiator
import gwf.api.util.ClosureUtil

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

        ClosureUtil.delegateFirst(cl, impl).call()

        tasks.add(impl)
    }

    @Override
    <T extends WorkflowTask> void task(
            String name,
            @DelegatesTo.Target Class<T> clazz,
            @DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure<?> cl) {

        def impl = instantiator.create(clazz)
        impl.setName(name)

        ClosureUtil.delegateFirst(cl, impl).call()

        tasks.add(impl)
    }

    @Override
    Collection<WorkflowTask> getTasks() {
        tasks
    }
}
