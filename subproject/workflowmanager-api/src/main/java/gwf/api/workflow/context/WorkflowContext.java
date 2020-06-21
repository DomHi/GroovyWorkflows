package gwf.api.workflow.context;

import gwf.api.workflow.context.exception.AmbiguousContextException;
import gwf.api.workflow.context.exception.ContextException;

import java.util.HashMap;
import java.util.Map;

public class WorkflowContext {

	private static final ThreadContext ctx = new ThreadContext();

	private WorkflowContext() {}

	public static <T> T get(Class<T> clazz) {
		T instance = find(clazz);
		if(instance == null) {
			throw new ContextException("No contextual object found for " + clazz);
		}
		return instance;
	}

	public static <T> T find(Class<T> clazz) {
		T instance = null;
		for(Map.Entry<Class<?>, Object> entry : getCtx().entrySet()) {
			if (clazz.isAssignableFrom(entry.getKey())) {
				if(instance == null) {
					instance = clazz.cast(entry.getValue());
				} else {
					throw new AmbiguousContextException("Multiple instances found for " + clazz);
				}
			}
		}
		return instance;
	}

	public static <T> void add(T config) {
		add(getCtxClass(config.getClass()), config);
	}

	public static <T,U> void add(Class<U> clazz,T config) {
		if (!clazz.isAssignableFrom(config.getClass())) {
			throw new ContextException("Given object is not of type " + clazz);
		}
		U instance = find(clazz);
		if (instance instanceof MergeableContext) {
			getCtx().put(clazz, ((MergeableContext) instance).merge(config));
			return;
		} else if (instance != null) {
			throw new ContextException("Contextual type already defined for " + clazz);
		}
		getCtx().put(clazz, config);
	}

	public static void clear() {
		ctx.remove();
	}

	private static Map<Class<?>, Object> getCtx() {
		return ctx.get();
	}

	private static Class<?> getCtxClass(Class<?> clazz) {
		ContextClass[] annotated = clazz.getAnnotationsByType(ContextClass.class);
		if(annotated.length > 1) {
			throw new ContextException("Multiple context classes not supported on " + clazz);
		} else if(annotated.length == 1) {
			return annotated[0].value();
		}
		return clazz;
	}

	private static final class ThreadContext extends ThreadLocal<Map<Class<?>, Object>> {
		@Override
		protected Map<Class<?>, Object> initialValue() {
			return new HashMap<>();
		}
	}
}
