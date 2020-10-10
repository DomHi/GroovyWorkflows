package gwf.util.task.sql.result;

import java.util.Iterator;

public class SelectionResultImpl<T> implements SelectionResult<T> {

	private final Class<T> clazz;

	public SelectionResultImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Class<T> getType() {
		return clazz;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public void close() throws Exception {

	}
}
