package gwf.workflows;

import gwf.api.WorkflowExecution;
import gwf.util.task.context.DefaultDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

@Slf4j
public class WorkflowHelper {

    private WorkflowHelper() {}

    public static void start(String workflow) {
        log.info("Start workflow " + workflow);
        WorkflowExecution execution = WorkflowExecution.builder()
            .addContextual(new DefaultDatabaseConfig("default", ds()))
            .locator(new WorkflowsLocatorImpl())
            .workflowName(workflow)
            .build();
        try {
            execution.execute();
        } catch (Exception e) {
            log.error("Exception while doing something.", e);
        }
    }

    private static DataSource ds() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:test;DB_CLOSE_ON_EXIT=FALSE");
        return ds;
    }
}
