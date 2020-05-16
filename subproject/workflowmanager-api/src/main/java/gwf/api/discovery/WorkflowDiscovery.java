package gwf.api.discovery;

import java.net.URI;

/**
 * Implementations of this class are used to locate workflows.
 * To add a custom {@code WorkflowDiscovery} provide a CDI injectable instance.
 */
public interface WorkflowDiscovery {

	/**
	 *
	 * @param ctx a context object which describes the requirements of the worklfow to discover.
	 * @return a {@code URI} describing the location of a workflow, or {@code null} if no workflow was discovered.
	 */
	URI find(WorkflowDiscoveryContext ctx);
}
