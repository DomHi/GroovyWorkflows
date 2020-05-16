package gwf.api.delegate;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.task.TaskConfig;
import gwf.api.workflow.WorkflowExecutionContext;
import org.slf4j.Logger;

public interface WorkflowDelegateBase {

	Logger getLog();

	WorkflowExecutionContext getContext();

	void tasks(@DelegatesTo(TaskConfig.class) Closure<?> config);
}
