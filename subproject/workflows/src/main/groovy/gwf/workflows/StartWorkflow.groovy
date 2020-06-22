package gwf.workflows

import groovy.util.logging.Slf4j
import gwf.api.WorkflowManager
import gwf.api.workflow.context.WorkflowContext
import gwf.util.task.context.DefaultDatabaseConfig

import javax.inject.Inject
import javax.naming.InitialContext
import javax.sql.DataSource

@Slf4j
class StartWorkflow {

    @Inject
    private WorkflowManager workflowManager

    void start(String workflow) {
        log.info "Start workflow $workflow"
        try {
            addDbContext()
            workflowManager.execute(workflow)
        } catch (Exception e) {
            log.error("Exception while doing something.", e)
        }
    }

    private void addDbContext() {
        WorkflowContext.add(new DefaultDatabaseConfig(ds()))
    }

    private DataSource ds() {
        return (DataSource) new InitialContext().lookup("java:jboss/datasources/ExampleDS")
    }
}
