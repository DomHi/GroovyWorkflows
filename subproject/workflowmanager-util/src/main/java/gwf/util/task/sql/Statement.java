package gwf.util.task.sql;

import org.jdbi.v3.core.Jdbi;

public interface Statement {

	void execute(Jdbi jdbi);
}
