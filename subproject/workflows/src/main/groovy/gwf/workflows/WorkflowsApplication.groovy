package gwf.workflows

import groovy.util.logging.Slf4j

@Slf4j
class WorkflowsApplication {

    static void main(String[] args) {
        log.info("main called")
        new StartWorkflow().start("myTest")
    }
}
