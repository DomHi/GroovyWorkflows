package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public interface SqlConfig {

	String getName();

	void jdbi(@DelegatesTo(Jdbi.class) Closure<?> cl);

	void jdbi(DataSource ds, @DelegatesTo(Jdbi.class) Closure<?> cl);

	void update(@DelegatesTo(UpdateStatement.class) Closure<?> cl);

	<T> Select<T> select(Class<T> clazz,
	                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl);

	<T> Select<T> selectBean(Class<T> clazz,
	                         @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl);

	void handle(@ClosureParams(value = SimpleType.class, options = "org.jdbi.v3.core.Handle") Closure<?> cl);
}
