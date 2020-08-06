package gwf.wfm.impl.task;

import gwf.api.task.WorkflowTask;
import gwf.api.task.instance.TaskInstantiator;

import java.util.Collection;

public class DefaultTaskContainer extends AbstractTaskContainer {

	public DefaultTaskContainer(TaskInstantiator instantiator) {
		super(instantiator);
	}

	public DefaultTaskContainer(Collection<WorkflowTask> tasks) {
		super(tasks);
	}
}
