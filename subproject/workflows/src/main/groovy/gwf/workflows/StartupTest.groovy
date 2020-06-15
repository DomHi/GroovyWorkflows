package gwf.workflows;

import gwf.api.WorkflowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.time.Duration;

@Singleton
@Startup
class StartupTest {

	private static final Logger log = LoggerFactory.getLogger(StartupTest.class);

	@Inject
	private WorkflowManager workflowManager;

	@Resource
	private TimerService timerService;

	@PostConstruct
	private void init() {
		timerService.createSingleActionTimer(
				Duration.ofSeconds(10L).toMillis(),
				new TimerConfig(null, false)
		);
	}

	@Timeout
	private void blubb(Timer timer) {
		log.info("timeout");
		try {
			workflowManager.execute("myTest");
		} catch (Exception e) {
			log.error("Exception while doing something.", e);
		}
	}
}
