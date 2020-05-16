package gwf.api.workflow;

public interface WorkflowExecutionContext {

	String getWorkflowName();

	String getRelease();

	String getSwVersion();

	String getTechnology();
}
