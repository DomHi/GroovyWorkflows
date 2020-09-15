package gwf.util.task.sql;

import org.jdbi.v3.core.Handle;

public abstract class Statement {

	protected abstract void execute(Handle handle);
}
