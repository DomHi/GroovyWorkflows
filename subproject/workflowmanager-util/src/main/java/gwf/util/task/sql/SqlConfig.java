package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.jdbi.v3.core.Jdbi;

import javax.sql.DataSource;

public interface SqlConfig {

	String getName();

	void jdbi(@DelegatesTo(Jdbi.class) Closure<?> cl);

	void jdbi(DataSource ds, @DelegatesTo(Jdbi.class) Closure<?> cl);

	void sql(@DelegatesTo(SimpleStatement.class) Closure<?> cl);

	<T> Select<T> select(Class<T> clazz,
	                     @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl);

	<T> Select<T> selectBean(Class<T> clazz,
	                         @DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "gwf.util.task.sql.Select<T>") Closure<?> cl);
}
