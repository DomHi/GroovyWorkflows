package gwf.api;

public class WorkflowManagerException extends RuntimeException {

	public WorkflowManagerException() {
		super();
	}

	public WorkflowManagerException(String msg) {
		super(msg);
	}

	public WorkflowManagerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
