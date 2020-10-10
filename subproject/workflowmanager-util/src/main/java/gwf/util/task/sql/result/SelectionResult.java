package gwf.util.task.sql.result;

/**
 * Represents the result of a select block, can be iterated over.
 *
 * @param <T>
 */
public interface SelectionResult<T> extends Iterable<T>, AutoCloseable {

	Class<T> getType();
}
