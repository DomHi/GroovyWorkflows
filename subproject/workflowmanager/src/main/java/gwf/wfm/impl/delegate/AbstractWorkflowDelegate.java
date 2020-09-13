package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.WorkflowManagerException;
import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskContainer;
import gwf.api.task.WorkflowTask;
import gwf.api.util.ClosureUtil;
import gwf.wfm.impl.file.loader.FileLoader;
import gwf.wfm.impl.phase.ConfigurationPhase;
import gwf.wfm.impl.task.CdiTaskInstantiator;
import gwf.wfm.impl.task.DefaultTaskContainer;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.wfm.impl.workflow.WorkflowLocator;

import javax.enterprise.inject.spi.CDI;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorkflowDelegate implements WorkflowDelegateBase {

	protected final WorkflowConfiguration config;

	protected final List<TaskContainer> taskContainers = new ArrayList<>();

	protected AbstractWorkflowDelegate(WorkflowConfiguration config) {
		this.config = config;
	}

	public abstract ExecutorConfig getExecutorConfig();

	@Override
	public Map<String, String> getEnv() {
		return config.getEnv();
	}

	@Override
	public void tasks(Closure<?> cl) {
		ConfigurationPhase.execute("tasks", () -> {
			TaskContainer newTasks = new DefaultTaskContainer(new CdiTaskInstantiator());
			ClosureUtil.delegateFirst(cl, newTasks).call();
			taskContainers.add(newTasks);
		});
	}

	@Override
	public void inline(String path) {
		URI inlineUri = locator().relative(config.getLocation(), path);
		AbstractWorkflowDelegate delegate = Delegation.inlined(
				this,
				new WorkflowConfigurationImpl(inlineUri, config.getEnv())
		);
		taskContainers.add(new DefaultTaskContainer(delegate.getTasks()));
	}

	@Override
	public URL url(String uri) {
		URI resolved = config.getLocation().resolve(uri);
		try {
			return resolved.toURL();
		} catch (MalformedURLException e) {
			throw new WorkflowManagerException(e);
		}
	}

	@Override
	public <T> T load(Class<T> type, String file) {
		FileLoader<T> loader = FileLoader.of(extension(file), type);
		return loader.load(url(file));
	}

	public List<WorkflowTask> getTasks() {
		List<WorkflowTask> tasks = new ArrayList<>();
		taskContainers.forEach(
				cfg -> tasks.addAll(cfg.getTasks())
		);
		return tasks;
	}

	protected WorkflowLocator locator() {
		return CDI.current().select(WorkflowLocator.class).get();
	}

	private String extension(String file) {
		String[] parts = file.split("\\.");
		return parts[parts.length - 1];
	}
}
