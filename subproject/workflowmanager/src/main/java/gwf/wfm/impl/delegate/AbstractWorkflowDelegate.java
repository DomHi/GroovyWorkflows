package gwf.wfm.impl.delegate;

import groovy.lang.Closure;
import gwf.api.WorkflowManagerException;
import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.executor.ExecutorConfig;
import gwf.api.executor.TaskExecutor;
import gwf.api.util.ClosureUtil;
import gwf.api.workflow.execution.WorkflowExecution;
import gwf.wfm.impl.file.loader.FileLoader;
import gwf.wfm.impl.phase.ConfigurationPhase;
import gwf.wfm.impl.task.AbstractTaskContainer;
import gwf.wfm.impl.task.CdiTaskInstantiator;
import gwf.wfm.impl.task.DefaultTaskContainer;
import gwf.wfm.impl.task.ExecutableTasks;
import gwf.wfm.impl.workflow.WorkflowConfiguration;
import gwf.wfm.impl.workflow.WorkflowConfigurationImpl;
import gwf.wfm.impl.workflow.locator.WorkflowLocator;

import javax.enterprise.inject.spi.CDI;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorkflowDelegate implements WorkflowDelegateBase, ExecutableTasks {

	private Closure<?> executionWrapper = null;

	protected final WorkflowConfiguration config;

	protected final List<ExecutableTasks> executables = new ArrayList<>();

	protected AbstractWorkflowDelegate(WorkflowConfiguration config) {
		this.config = config;
	}

	public abstract ExecutorConfig getExecutorConfig();

	@Override
	public Map<String, String> getEnv() {
		return config.getEnv();
	}

	@Override
	public void wrapExecution(Closure<?> cl) {
		ConfigurationPhase.executeExclusive("wrapExecution", () -> {
			if (executionWrapper != null) {
				throw new WorkflowManagerException("Multiple execution wrappers not supported.");
			}
			executionWrapper = cl;
		});
	}

	@Override
	public void tasks(Closure<?> cl) {
		ConfigurationPhase.execute("tasks", () -> {
			AbstractTaskContainer newTasks = new DefaultTaskContainer(new CdiTaskInstantiator());
			ClosureUtil.delegateFirst(cl, newTasks).call();
			executables.add(newTasks);
		});
	}

	@Override
	public void inline(String path) {
		ConfigurationPhase.inline(() -> {
			URI inlineUri = locator().relative(config.getLocation(), path);
			AbstractWorkflowDelegate delegate = Delegation.inlined(
					this,
					new WorkflowConfigurationImpl(inlineUri, config.getEnv())
			);
			executables.add(delegate);
		});
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

	@Override
	public <T> T cdi(Class<T> type) {
		return CDI.current().select(type).get();
	}

	@Override
	public void run(TaskExecutor defaultExecutor, Map<String, Object> properties) {
		if (executionWrapper != null) {
			executionWrapper.call(
					(WorkflowExecution) () -> runInternal(defaultExecutor, properties)
			);
		} else {
			runInternal(defaultExecutor, properties);
		}
	}

	private void runInternal(TaskExecutor defaultExecutor, Map<String, Object> properties) {
		TaskExecutor configured = getExecutorConfig().getExecutor();

		TaskExecutor exe = configured != null ? configured : defaultExecutor;

		Map<String, Object> merged = new HashMap<>();
		merged.putAll(properties);
		merged.putAll(getExecutorConfig().getProperties());
		executables.forEach(
				e -> e.run(exe, merged)
		);
	}

	protected WorkflowLocator locator() {
		return CDI.current().select(WorkflowLocator.class).get();
	}

	private String extension(String file) {
		String[] parts = file.split("\\.");
		return parts[parts.length - 1];
	}
}
