package gwf.workflows

import groovy.util.logging.Slf4j
import gwf.api.WorkflowBuilder
import gwf.api.WorkflowManager
import gwf.api.workflow.context.WorkflowContext
import gwf.util.task.context.DefaultDatabaseConfig

import javax.ejb.Stateless
import javax.inject.Inject
import javax.naming.InitialContext
import javax.sql.DataSource

@Slf4j
@Stateless
class StartWorkflow {

    void start(String workflow) {
        log.info "Start workflow $workflow"
        try {
            WorkflowBuilder.create()
                .context(new DefaultDatabaseConfig(ds()))
                .locator(new WorkflowsLocatorImpl())
                .workflowName(workflow)
                .build()
                .execute()
        } catch (Exception e) {
            log.error("Exception while doing something.", e)
        }
    }

    private DataSource ds() {
        return (DataSource) new InitialContext().lookup("java:jboss/datasources/ExampleDS")
    }
}
