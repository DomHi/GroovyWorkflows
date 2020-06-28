package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;
import gwf.api.util.ClosureUtil;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;

import java.util.List;

public class Select<T> extends AbstractJdbiStatement {

	private final Class<T> clazz;
	private String statement = null;

	private Closure<?> thenCl = null;

	public Select(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setStatement(String stmt) {
		if(statement == null) {
			statement = stmt;
		}
	}

	public void then(@ClosureParams(value=FromString.class, options="List<T>") Closure<?> cl) {
		thenCl = ClosureUtil.delegateFirst(cl);
	}

	@Override
	public void execute(Jdbi jdbi) {

		List<T> result = jdbi.withHandle(
				handle -> {
					Query q = handle.createQuery(statement);

					getDefine().forEach(q::define);
					getBind().forEach(q::bind);
					return q.mapTo(clazz).list();
				}
		);

		if(thenCl != null) {
			thenCl.call(result);
		}
	}
}
