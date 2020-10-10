package gwf.util.task.sql.config;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.transform.stc.ClosureParams;
import groovy.transform.stc.FromString;

public interface SelectConfig extends JdbiBindingProvider, JdbiDefinitionProvider {

	void setStatement(String stmt);

	interface Then<T> {

		void then(@ClosureParams(value = FromString.class, options = "java.util.List<T>") Closure<?> cl);

		void useBatch(
				@ClosureParams(value = FromString.class, options = "java.util.Iterable<T>")
				@DelegatesTo(BatchConfig.class)
						Closure<?> cl
		);
	}
}
