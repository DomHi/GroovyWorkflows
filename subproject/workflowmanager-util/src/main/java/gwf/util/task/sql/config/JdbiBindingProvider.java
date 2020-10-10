package gwf.util.task.sql.config;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Map;

public interface JdbiBindingProvider {

	Map<String, Object> getBind();

	void bind(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl);
}
