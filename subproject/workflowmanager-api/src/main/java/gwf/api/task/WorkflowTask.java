package gwf.api.task;

public abstract class WorkflowTask {

	private String name;

	public abstract <T extends TaskExecutionResult> T execute();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
