package gwf.wfm.task

import gwf.api.task.TaskExecutionResult
import gwf.api.task.WorkflowTask
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DummyTaskImpl extends WorkflowTask {

    private static final Logger log = LoggerFactory.getLogger(DummyTaskImpl);

    private String dummyProperty;

    def setDummyProperty(String prop) {
        dummyProperty = prop
    }

    def getDummyProperty() {
        dummyProperty
    }

    @Override
    <T extends TaskExecutionResult> T execute() {

        log.info "execute $name"

        return null
    }
}
