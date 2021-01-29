package gwf.workflows;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class WorkflowsApplication {

    public static void main(String[] args) {
        log.info("start execution");

        Instant before = Instant.now();

        try {
            new StartWorkflow().start("myTest");
        } finally {
            log.info("Execution took: {} ms", ChronoUnit.MILLIS.between(before, Instant.now()));
        }
    }
}
