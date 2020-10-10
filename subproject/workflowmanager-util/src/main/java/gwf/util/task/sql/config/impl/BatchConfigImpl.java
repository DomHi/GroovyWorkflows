package gwf.util.task.sql.config.impl;

import groovy.lang.Closure;
import gwf.api.WorkflowManagerException;
import gwf.api.util.ClosureUtil;
import gwf.util.task.sql.config.BatchConfig;

import java.util.HashMap;
import java.util.Map;

public class BatchConfigImpl implements BatchConfig {

	private String stmt = null;
	private int batchSize = 2000;
	private final Map<String, Object> definitions = new HashMap<>();

	private Iterable<Object> bindings = null;

	@Override
	public void setStatement(String stmt) {
		if (this.stmt == null) {
			this.stmt = stmt;
		}
	}

	@Override
	public void setBatchSize(int size) {
		this.batchSize = size;
	}

	public int getBatchSize() {
		return batchSize;
	}

	@Override
	public String getStatement() {
		return stmt;
	}

	@Override
	public Map<String, Object> getDefine() {
		return definitions;
	}

	@Override
	public void define(Closure<?> cl) {
		ClosureUtil.delegateFirst(cl, definitions).call();
	}

	@Override
	public void bind(Iterable<Object> it) {
		if (this.bindings != null) {
			throw new WorkflowManagerException("Binding already defined.");
		}
		this.bindings = it;
	}

	public Iterable<Object> getBindings() {
		return bindings;
	}
}
