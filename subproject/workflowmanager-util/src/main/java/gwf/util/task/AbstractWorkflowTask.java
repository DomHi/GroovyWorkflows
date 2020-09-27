package gwf.util.task;

import gwf.api.task.WorkflowTask;

public abstract class AbstractWorkflowTask<C> implements WorkflowTask<C> {

	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
