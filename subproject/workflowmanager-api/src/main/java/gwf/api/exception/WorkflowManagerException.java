package gwf.api.exception;

public class WorkflowManagerException extends RuntimeException {

	public WorkflowManagerException(String message) {
		super(message);
	}

	public WorkflowManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
