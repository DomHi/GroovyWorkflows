package gwf.util.task.sql;

import gwf.util.task.sql.config.impl.BatchConfigImpl;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;

public class BatchStatement implements HandleConsumer {

	private final BatchConfigImpl config;

	public BatchStatement(BatchConfigImpl config) {
		this.config = config;
	}

	@Override
	public void apply(Handle handle) {

		try (PreparedBatch batch = handle.prepareBatch(config.getStatement())) {
			config.getDefine().forEach(batch::define);

			for (Object binding : config.getBindings()) {
				batch.bindBean(binding).add();

				if (batch.size() >= config.getBatchSize()) {
					batch.execute();
				}
			}

			if (batch.size() > 0) {
				batch.execute();
			}
		}
	}
}
