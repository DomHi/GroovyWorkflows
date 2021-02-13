package gwf.workflows;

import gwf.workflows.ui.DemoUI;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.logging.LogManager;

@Slf4j
public class WorkflowsApplication {

    // init logging
    static {
        try {
            LogManager.getLogManager().readConfiguration(
                    WorkflowsApplication.class.getClassLoader().getResourceAsStream("logging.properties")
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        if (doDefault(args)) {
            runDefault();
            return;
        }

        DemoUI.start();
    }

    private static boolean doDefault(String[] args) {
        for(String arg : args) {
            if("-d".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    private static void runDefault() {
        log.info("start execution");

        Instant before = Instant.now();

        try {
            WorkflowHelper.start("myTest");
        } finally {
            log.info("Execution took: {} ms", ChronoUnit.MILLIS.between(before, Instant.now()));
        }
    }
}
