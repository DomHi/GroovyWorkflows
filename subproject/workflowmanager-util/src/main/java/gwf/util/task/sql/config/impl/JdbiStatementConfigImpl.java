package gwf.util.task.sql.config.impl;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.util.ClosureUtil;
import gwf.util.task.sql.config.JdbiBindingProvider;
import gwf.util.task.sql.config.JdbiDefinitionProvider;

import java.util.HashMap;
import java.util.Map;

public abstract class JdbiStatementConfigImpl implements JdbiBindingProvider, JdbiDefinitionProvider {

	private final Map<String, Object> binding = new HashMap<>();
	private final Map<String, Object> definitions = new HashMap<>();

	@Override
	public Map<String, Object> getBind() {
		return binding;
	}

	@Override
	public Map<String, Object> getDefine() {
		return definitions;
	}

	@Override
	public void bind(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl) {
		ClosureUtil.delegateFirst(cl, binding).call();
	}

	@Override
	public void define(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl) {
		ClosureUtil.delegateFirst(cl, definitions).call();
	}
}
