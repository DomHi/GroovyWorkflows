package gwf.workflows.ui

import groovy.util.logging.Slf4j
import gwf.workflows.StartWorkflow

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@Named("workflows")
@ApplicationScoped
@Slf4j
class WorkflowBean {

    @Inject
    private StartWorkflow startWorkflow;

    private String selected = null;

    private List<String> workflows = ["myTest"]

    void setSelected(String selected) {
        this.selected = selected
    }

    String getSelected() {
        return this.selected
    }

    List<String> getList() {
        return workflows
    }

    Object submit() {
        if(selected != null) {
            startWorkflow.start(selected)
        }
        return null
    }
}
