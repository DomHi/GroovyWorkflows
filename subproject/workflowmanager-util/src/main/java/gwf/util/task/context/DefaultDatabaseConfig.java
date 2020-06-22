package gwf.util.task.context;

import javax.sql.DataSource;

public class DefaultDatabaseConfig {

	private final DataSource dflt;

	public DefaultDatabaseConfig(DataSource dflt) {
		this.dflt = dflt;
	}

	public DataSource getDefaultDs() {
		return dflt;
	}
}
