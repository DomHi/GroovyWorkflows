package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.discovery.WorkflowDiscoveryContext;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskConfig;
import gwf.api.task.WorkflowTask;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.WorkflowConfiguration;
import gwf.api.workflow.context.WorkflowContext;
import gwf.wfm.impl.executor.ExecutorConfigImpl;
import gwf.wfm.impl.task.CdiTaskInstantiator;
import gwf.wfm.impl.task.TaskConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorkflowDelegateImpl implements WorkflowDelegateBase {

	private Logger log;

	private final WorkflowConfiguration config;
	private ExecutorConfig executorConfig;

	private final List<TaskConfig> taskConfigs = new ArrayList<>();

	public WorkflowDelegateImpl(WorkflowConfiguration config) {
		this.config = config;
		initLogging();
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public void executor(Closure<?> cl) {
		if (executorConfig == null) {
			executorConfig = new ExecutorConfigImpl();
		}
		ClosureUtil.delegateFirst(cl, executorConfig).call();
	}

	@Override
	public void tasks(Closure<?> cl) {
		TaskConfig newTasks = new TaskConfigImpl(new CdiTaskInstantiator());
		ClosureUtil.delegateFirst(cl, newTasks).call();
		taskConfigs.add(newTasks);
	}

	@Override
	public void inline(String path) {
		// implement utility which does the stuff listed below
		// TODO locate inline-workflow
		// TODO load it as a script
		// TODO create InlineWorkflowDelegate
		// TODO configure delegate using workflow
		// TODO add tasks of delgate to this worklow's tasks
	}

	public Collection<WorkflowTask> getTasks() {
		Collection<WorkflowTask> tasks = new ArrayList<>();
		taskConfigs.forEach(
				cfg -> tasks.addAll(cfg.getTasks())
		);
		return tasks;
	}

	private void initLogging() {
		log = LoggerFactory.getLogger(getLoggerName());
	}

	private String getLoggerName() {
		WorkflowDiscoveryContext ctx = WorkflowContext.get(WorkflowDiscoveryContext.class);
		return String.format("wfm.%s", ctx.getName());
	}
}
