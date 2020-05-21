package gwf.api.workflow;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface WorkflowExecutionContext {

	String getWorkflowName();

	Optional<String> getRelease();

	Optional<String> getSwVersion();

	Optional<String> getTechnology();
}
