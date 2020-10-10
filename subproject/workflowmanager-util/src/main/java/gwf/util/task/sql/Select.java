package gwf.util.task.sql;

import gwf.api.WorkflowManagerException;
import gwf.api.util.ClosureUtil;
import gwf.util.task.sql.config.impl.BatchConfigImpl;
import gwf.util.task.sql.config.impl.SelectConfigImpl;
import gwf.util.task.sql.config.impl.SelectConfigImpl.ThenImpl;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.List;

public class Select<T> implements HandleConsumer {

	private final SelectConfigImpl<T> config;

	public Select(SelectConfigImpl<T> config) {
		this.config = config;
	}

	@Override
	public void apply(Handle handle) {

		ThenImpl<?> then = config.getThen();

		if (then.getThenClosure() == null && then.getBatchClosure() == null) {
			throw new WorkflowManagerException("THEN closure is missing.");
		}

		List<T> result;

		try (Query q = handle.createQuery(config.getStatement())) {
			config.getDefine().forEach(q::define);
			config.getBind().forEach(q::bind);
			if (config.isBean()) {
				result = q.mapToBean(config.getClazz()).list();
			} else {
				result = q.mapTo(config.getClazz()).list();
			}
		}

		if (then.getThenClosure() != null) {
			then.getThenClosure().call(result);
		} else {
			BatchConfigImpl batchConfig = new BatchConfigImpl();
			ClosureUtil.delegateFirst(then.getBatchClosure(), batchConfig).call(result);
			BatchStatement stmt = new BatchStatement(batchConfig);
			stmt.apply(handle);
		}
	}
}
