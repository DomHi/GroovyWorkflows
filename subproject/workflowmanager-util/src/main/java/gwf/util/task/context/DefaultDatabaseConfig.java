package gwf.util.task.context;

import gwf.api.WorkflowManagerException;
import gwf.api.workflow.context.ContextClass;

import javax.sql.DataSource;
import java.util.Optional;

/**
 * Default implementation for {@code ContextualDataSources} which supports only a single DataSource,
 * that is also used as default.
 */
@ContextClass(ContextualDataSources.class)
public class DefaultDatabaseConfig implements ContextualDataSources {

	private final String name;
	private final DataSource dflt;

	public DefaultDatabaseConfig(String name, DataSource dflt) {
		this.name = name;
		this.dflt = dflt;
	}

	public DataSource getDefaultDs() {
		return dflt;
	}

	@Override
	public DataSource get(String name) {
		if (this.name.equals(name)) {
			return dflt;
		}
		throw new WorkflowManagerException("Invalid workflow name: " + name);
	}

	@Override
	public Optional<DataSource> find(String name) {
		if (this.name.equals(name)) {
			return Optional.of(dflt);
		}
		return Optional.empty();
	}

	@Override
	public Optional<DataSource> getDefault() {
		return Optional.of(dflt);
	}
}
