package gwf.util.task.sql.config.impl;

import groovy.lang.Closure;
import gwf.api.WorkflowManagerException;
import gwf.util.task.sql.config.SelectConfig;

public class SelectConfigImpl<T> extends JdbiStatementConfigImpl implements SelectConfig {

	private final Class<T> clazz;
	private final boolean isBean;

	private final ThenImpl<T> then;

	private String statement = null;

	public SelectConfigImpl(Class<T> clazz, boolean isBean) {
		this.clazz = clazz;
		this.isBean = isBean;

		this.then = new ThenImpl<>();
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public boolean isBean() {
		return isBean;
	}

	@Override
	public void setStatement(String stmt) {
		if (statement == null) {
			statement = stmt;
		}
	}

	public String getStatement() {
		return statement;
	}

	public ThenImpl<T> getThen() {
		return then;
	}

	public static class ThenImpl<T> implements SelectConfig.Then<T> {

		private Closure<?> thenClosure = null;
		private Closure<?> batchClosure = null;

		@Override
		public void then(Closure<?> cl) {
			if (thenClosure != null || batchClosure != null) {
				throw new WorkflowManagerException("Multiple select/batch closures not supported.");
			}
			this.thenClosure = cl;
		}

		@Override
		public void useBatch(Closure<?> cl) {
			if (thenClosure != null || batchClosure != null) {
				throw new WorkflowManagerException("Multiple select/batch closures not supported.");
			}
			this.batchClosure = cl;
		}

		public boolean isBatch() {
			return batchClosure != null;
		}

		public Closure<?> getThenClosure() {
			return thenClosure;
		}

		public Closure<?> getBatchClosure() {
			return batchClosure;
		}
	}
}
