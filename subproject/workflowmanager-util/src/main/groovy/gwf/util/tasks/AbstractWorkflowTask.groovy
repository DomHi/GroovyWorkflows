package gwf.util.tasks

import gwf.api.task.WorkflowTask

abstract class AbstractWorkflowTask implements WorkflowTask {

    private String name

    @Override
    String getName() {
        name
    }

    @Override
    void setName(String name) {
        this.name = name
    }
}
