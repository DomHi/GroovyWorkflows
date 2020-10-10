package gwf.util.task.sql.config.impl;

import gwf.util.task.sql.config.UpdateConfig;

public class UpdateConfigImpl extends JdbiStatementConfigImpl implements UpdateConfig {

	private String stmt = null;

	@Override
	public void setStatement(String stmt) {
		if (this.stmt == null) {
			this.stmt = stmt;
		}
	}

	@Override
	public String getStatement() {
		return stmt;
	}
}
