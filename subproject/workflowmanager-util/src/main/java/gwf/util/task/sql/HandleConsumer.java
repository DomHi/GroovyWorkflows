package gwf.util.task.sql;

import org.jdbi.v3.core.Handle;

public interface HandleConsumer {

	void apply(Handle handle);
}
