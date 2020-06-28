package gwf.util.task.sql;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;

public class SimpleStatement extends AbstractJdbiStatement {

	private final String stmt;

	public SimpleStatement(String stmt) {
		this.stmt = stmt;
	}

	@Override
	public void execute(Jdbi jdbi) {
		jdbi.useHandle(
				handle -> {
					Update u = handle.createUpdate(stmt);
					getDefine().forEach(u::define);
					getBind().forEach(u::bind);
					u.execute();
				}
		);
	}
}
