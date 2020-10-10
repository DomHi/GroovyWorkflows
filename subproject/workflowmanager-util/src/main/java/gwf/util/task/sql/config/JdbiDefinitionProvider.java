package gwf.util.task.sql.config;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;

import java.util.Map;

public interface JdbiDefinitionProvider {

	Map<String, Object> getDefine();

	void define(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl);
}
