package gwf.wfm.impl.task;

import groovy.lang.Closure;
import gwf.api.executor.TaskExecutor;
import gwf.api.task.TaskContainer;
import gwf.api.task.WorkflowTask;
import gwf.api.task.instance.TaskInstantiator;
import gwf.api.util.ClosureUtil;
import gwf.wfm.impl.executor.provider.TaskProviderImpl;
import gwf.wfm.impl.phase.ConfigurationPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractTaskContainer implements TaskContainer, ExecutableTasks {

	private TaskInstantiator instantiator;

	private TaskExecutor executor = null;

	private final List<WorkflowTask<?>> tasks = new ArrayList<>();

	protected AbstractTaskContainer(TaskInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public void setInstantiator(TaskInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public void setExecutor(TaskExecutor executor) {
		this.executor = executor;
	}

	@Override
	public <T, U extends WorkflowTask<T>> void task(Class<U> clazz, Closure<?> cl) {
		ConfigurationPhase.execute("task", () -> {
			U taskImpl = instantiator.create(clazz);
			// TODO set generated name in taskImpl
			taskImpl.configure(
					cfg -> ClosureUtil.delegateFirst(cl, cfg).call()
			);
			tasks.add(taskImpl);
		});
	}

	@Override
	public <T, U extends WorkflowTask<T>> void task(String name, Class<U> clazz, Closure<?> cl) {
		ConfigurationPhase.execute("task", () -> {
			U impl = instantiator.create(clazz);
			impl.setName(name);
			impl.configure(
					cfg -> ClosureUtil.delegateFirst(cl, cfg).call()
			);
			tasks.add(impl);
		});
	}

	@Override
	public void run(TaskExecutor defaultExecutor, Map<String, Object> properties) {
		TaskExecutor exe = executor != null ? executor : defaultExecutor;
		exe.execute(new TaskProviderImpl(tasks), properties);
	}
}
