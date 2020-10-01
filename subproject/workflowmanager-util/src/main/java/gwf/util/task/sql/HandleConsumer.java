package gwf.util.task.sql;

import org.jdbi.v3.core.Handle;

public abstract class HandleConsumer {

	protected abstract void apply(Handle handle);
}
