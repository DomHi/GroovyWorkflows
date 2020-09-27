package gwf.api.task;

import java.util.function.Consumer;

/**
 * Base interface for all worklow tasks.
 */
public interface WorkflowTask<C> {

	String getName();

	void setName(String name);

	void configure(Consumer<C> c);

	TaskExecutionResult execute();
}
