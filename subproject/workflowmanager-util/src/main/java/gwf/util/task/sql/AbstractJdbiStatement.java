package gwf.util.task.sql;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJdbiStatement implements Statement {

	private final Map<String, Object> binding = new HashMap<>();
	private final Map<String, Object> definitions = new HashMap<>();

	public Map<String, Object> getBind() {
		return binding;
	}

	public Map<String, Object> getDefine() {
		return definitions;
	}
}
