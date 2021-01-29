package gwf.workflows

import groovy.util.logging.Slf4j

import java.time.Instant
import java.time.temporal.ChronoUnit

@Slf4j
class WorkflowsApplication {

    static void main(String[] args) {
        log.info("main called")

        Instant before = Instant.now();

        try {
            new StartWorkflow().start("myTest")
        } finally {
            log.info("Execution took: {} ms", ChronoUnit.MILLIS.between(before, Instant.now()))
        }
    }
}
