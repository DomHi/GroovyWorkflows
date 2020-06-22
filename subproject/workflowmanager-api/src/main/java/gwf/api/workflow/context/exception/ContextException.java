package gwf.api.workflow.context.exception;

public class ContextException extends RuntimeException {

	public ContextException() {
		super();
	}

	public ContextException(String msg) {
		super(msg);
	}

	public ContextException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
