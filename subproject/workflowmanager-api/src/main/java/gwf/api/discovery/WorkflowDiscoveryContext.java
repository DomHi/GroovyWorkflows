package gwf.api.discovery;

import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public interface WorkflowDiscoveryContext {

	String getName();

	Optional<String> getRelease();
	Optional<String> getSwVersion();

	Optional<String> getTechnology();
}
