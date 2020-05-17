package gwf.api.delegate;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskConfig;
import gwf.api.workflow.WorkflowExecutionContext;
import org.slf4j.Logger;

public interface WorkflowDelegateBase {

	Logger getLog();

	WorkflowExecutionContext getContext();

	void executor(@DelegatesTo(value = ExecutorConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> cl);

	void tasks(@DelegatesTo(value = TaskConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> config);
}
