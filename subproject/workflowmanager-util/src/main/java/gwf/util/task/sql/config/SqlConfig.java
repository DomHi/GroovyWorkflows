package gwf.util.task.sql.config;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.SimpleType;
import org.jdbi.v3.core.Jdbi;

public interface SqlConfig {

	String getName();

	void jdbi(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Jdbi.class) Closure<?> cl);

	void jdbi(Object ds, @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = Jdbi.class) Closure<?> cl);

	void update(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = UpdateConfig.class) Closure<?> cl);

	void insertBatch(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = BatchConfig.class) Closure<?> cl);

	<T> SelectConfig.Then<T> select(Class<T> clazz,
	                                @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = SelectConfig.class) Closure<?> cl);

	<T> SelectConfig.Then<T> selectBean(Class<T> clazz,
	                                    @DelegatesTo(strategy = Closure.DELEGATE_FIRST, value = SelectConfig.class) Closure<?> cl);

	void handle(@ClosureParams(value = SimpleType.class, options = "org.jdbi.v3.core.Handle") Closure<?> cl);
}
