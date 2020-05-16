package gwf.wfm.task

import gwf.api.task.TaskConfig
import gwf.api.task.WorkflowTask

import javax.enterprise.inject.spi.CDI

class TaskConfigImpl implements TaskConfig {

    private List<WorkflowTask> tasks = new ArrayList<>()

    @Override
    <T extends WorkflowTask> void task(Class<T> clazz, Closure<?> cl) {
        def instance = CDI.current().select(clazz)
        if(instance.unsatisfied) {
            throw new IllegalArgumentException("Invalid class $clazz")
        }
        def impl = instance.get()

        def hydrated = cl.rehydrate(impl, this, this)
        hydrated.call()

        tasks.add(impl)
    }

    @Override
    <T extends WorkflowTask> Collection<T> getTasks() {
        tasks as Collection<T>
    }
}
