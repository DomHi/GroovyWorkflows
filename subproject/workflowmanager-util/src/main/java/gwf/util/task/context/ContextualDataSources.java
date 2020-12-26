package gwf.util.task.context;

import javax.sql.DataSource;
import java.util.Optional;

public interface ContextualDataSources {

    /**
     * Look up DataSource. If it is not found an Exception is thrown.
     *
     * @param name used to look up {@code DataSource}
     * @return {@code DataSource} identified by given name
     */
    DataSource get(String name);

    /**
     * @param name used to look up {@code DataSource}
     * @return {@code DataSource} identified by given name, or emtpy {@code Optional} if lookup fails
     */
    Optional<DataSource> find(String name);

    /**
     * @return default DataSource, or empty {@code Optional} if there is none
     */
    Optional<DataSource> getDefault();
}
