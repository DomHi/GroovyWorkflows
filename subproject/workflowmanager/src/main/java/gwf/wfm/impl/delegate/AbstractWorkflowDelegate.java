package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskContainer;
import gwf.api.task.WorkflowTask;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.WorkflowConfiguration;
import gwf.wfm.impl.task.CdiTaskInstantiator;
import gwf.wfm.impl.task.DefaultTaskContainer;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.wfm.impl.workflow.WorkflowLocator;

import javax.enterprise.inject.spi.CDI;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractWorkflowDelegate implements WorkflowDelegateBase {

	protected final WorkflowConfiguration config;

	protected final List<TaskContainer> taskContainers = new ArrayList<>();

	protected AbstractWorkflowDelegate(WorkflowConfiguration config) {
		this.config = config;
	}

	public abstract ExecutorConfig getExecutorConfig();

	@Override
	public void tasks(Closure<?> cl) {
		TaskContainer newTasks = new DefaultTaskContainer(new CdiTaskInstantiator());
		ClosureUtil.delegateFirst(cl, newTasks).call();
		taskContainers.add(newTasks);
	}

	@Override
	public void inline(String path) {
		getLogger().info("inline called with {}", path);
		URI inlineUri = locator().relative(config.getLocation(), path);
		AbstractWorkflowDelegate delgate = Delegation.inlined(this, new WorkflowConfigurationImpl(inlineUri));
		taskContainers.add(new DefaultTaskContainer(delgate.getTasks()));
	}

	public Collection<WorkflowTask> getTasks() {
		Collection<WorkflowTask> tasks = new ArrayList<>();
		taskContainers.forEach(
				cfg -> tasks.addAll(cfg.getTasks())
		);
		return tasks;
	}

	protected WorkflowLocator locator() {
		return CDI.current().select(WorkflowLocator.class).get();
	}
}
