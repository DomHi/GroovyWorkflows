package gwf.wfm.impl.executor.provider;

import gwf.api.executor.provider.TaskProvider;
import gwf.api.task.WorkflowTask;

import java.util.Iterator;
import java.util.List;

public class TaskProviderImpl implements TaskProvider {

	private final List<WorkflowTask> tasks;

	public TaskProviderImpl(List<WorkflowTask> tasks) {
		this.tasks = tasks;
	}

	@Override
	public int getTaskCount() {
		return tasks.size();
	}

	@Override
	public Iterator<WorkflowTask> iterator() {
		return tasks.iterator();
	}
}
