package gwf.api.workflow.context;

import java.util.HashMap;
import java.util.Map;

public class WorkflowContext {

	private static final ThreadContext ctx = new ThreadContext();

	public <T> T get(Class<T> clazz) {
		Map<Class<?>, Object> m = getCtx();
		return null;
	}

	public <T> T find(Class<T> clazz) {
		Map<Class<?>, Object> m = getCtx();
		return null;
	}

	public <T> void add(T config) {

	}

	public <T extends U,U> void add(Class<U> clazz,T config) {

	}

	private Map<Class<?>, Object> getCtx() {
		return ctx.get();
	}

	private static final class ThreadContext extends ThreadLocal<Map<Class<?>, Object>> {
		@Override
		protected Map<Class<?>, Object> initialValue() {
			return new HashMap<>();
		}
	}
}
