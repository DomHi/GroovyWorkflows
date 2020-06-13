package gwf.wfm.task

import gwf.api.task.WorkflowTask
import gwf.api.task.instance.TaskInstantiator

import javax.enterprise.inject.spi.CDI

class CdiTaskInstantiator implements TaskInstantiator {

    @Override
    <T extends WorkflowTask> T create(Class<T> clazz) {
        def instance = CDI.current().select(clazz)

        if(instance.unsatisfied) {
            throw new IllegalArgumentException("Invalid $clazz")
        }
        return instance.get()
    }
}
