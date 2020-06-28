package gwf.util.task.sql;

import org.jdbi.v3.core.Jdbi;

public class SimpleStatement extends AbstractJdbiStatement {

	private final String stmt;

	public SimpleStatement(String stmt) {
		this.stmt = stmt;
	}

	@Override
	public void execute(Jdbi jdbi) {
		jdbi.useHandle(
				handle -> handle.createUpdate(stmt).execute()
		);
	}
}
