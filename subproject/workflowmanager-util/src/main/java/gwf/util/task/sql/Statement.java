package gwf.util.task.sql;

import org.jdbi.v3.core.Jdbi;

public abstract class Statement {

    protected abstract void execute(Jdbi jdbi);
}
