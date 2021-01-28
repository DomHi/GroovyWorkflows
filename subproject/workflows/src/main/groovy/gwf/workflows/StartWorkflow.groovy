package gwf.workflows

import groovy.util.logging.Slf4j
import gwf.api.WorkflowExecution
import gwf.util.task.context.DefaultDatabaseConfig

import javax.ejb.Stateless
import javax.naming.InitialContext
import javax.sql.DataSource

@Slf4j
@Stateless
class StartWorkflow {

    void start(String workflow) {
        log.info "Start workflow $workflow"
        WorkflowExecution execution = WorkflowExecution.builder()
            .contextual(new DefaultDatabaseConfig("default", ds()))
            .locator(new WorkflowsLocatorImpl())
            .workflowName(workflow)
            .build();
        try {
            execution.execute();
        } catch (Exception e) {
            log.error("Exception while doing something.", e)
        }
    }

    private DataSource ds() {
        return (DataSource) new InitialContext().lookup("java:jboss/datasources/ExampleDS")
    }
}
