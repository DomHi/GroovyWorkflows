package gwf.wfm.impl.task;

import groovy.lang.Closure;
import gwf.api.task.TaskConfig;
import gwf.api.task.WorkflowTask;
import gwf.api.task.instance.TaskInstantiator;
import gwf.api.util.ClosureUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TaskConfigImpl implements TaskConfig {

	private TaskInstantiator instantiator;

	private List<WorkflowTask> tasks = new ArrayList<>();

	public TaskConfigImpl(TaskInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public void setTaskInstantiator(TaskInstantiator instantiator) {
		this.instantiator = instantiator;
	}

	@Override
	public TaskInstantiator getTaskInstantiator() {
		return instantiator;
	}

	@Override
	public <T extends WorkflowTask> void task(Class<T> clazz, Closure<?> cl) {
		T impl = instantiator.create(clazz);
		ClosureUtil.delegateFirst(cl, impl).call();
		tasks.add(impl);
	}

	@Override
	public <T extends WorkflowTask> void task(String name, Class<T> clazz, Closure<?> cl) {
		T impl = instantiator.create(clazz);
		impl.setName(name);
		ClosureUtil.delegateFirst(cl, impl).call();
		tasks.add(impl);
	}

	@Override
	public Collection<WorkflowTask> getTasks() {
		return tasks;
	}
}