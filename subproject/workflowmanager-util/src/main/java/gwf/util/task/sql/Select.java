package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;
import gwf.api.WorkflowManagerException;
import gwf.api.util.ClosureUtil;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.List;

public class Select<T> extends AbstractJdbiStatement {

	private final Class<T> clazz;
	private final boolean isBean;

	private String statement = null;

	private Closure<?> thenCl = null;

	public Select(Class<T> clazz, boolean isBean) {
		this.clazz = clazz;
		this.isBean = isBean;
	}

	public void setStatement(String stmt) {
		if (statement == null) {
			statement = stmt;
		}
	}

	public void then(@ClosureParams(value = FromString.class, options = "java.util.List<T>") Closure<?> cl) {
		if (thenCl != null) {
			throw new WorkflowManagerException("Multiple THEN closures not supported.");
		}
		thenCl = ClosureUtil.delegateFirst(cl);
	}

	@Override
	protected void execute(Handle handle) {

		if (thenCl == null) {
			throw new WorkflowManagerException("THEN closure is missing.");
		}

		List<T> result;

		try (Query q = handle.createQuery(statement)) {
			getDefine().forEach(q::define);
			getBind().forEach(q::bind);
			if (isBean) {
				result = q.mapToBean(clazz).list();
			} else {
				result = q.mapTo(clazz).list();
			}
		}

		thenCl.call(result);
	}
}
