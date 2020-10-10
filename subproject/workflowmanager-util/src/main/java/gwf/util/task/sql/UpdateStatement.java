package gwf.util.task.sql;

import gwf.util.task.sql.config.impl.UpdateConfigImpl;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Update;

public class UpdateStatement implements HandleConsumer {

	private UpdateConfigImpl config = null;

	public UpdateStatement(UpdateConfigImpl config) {
		this.config = config;
	}

	@Override
	public void apply(Handle handle) {
		try (Update u = handle.createUpdate(config.getStatement())) {
			config.getDefine().forEach(u::define);
			config.getBind().forEach(u::bind);
			u.execute();
		}
	}
}
