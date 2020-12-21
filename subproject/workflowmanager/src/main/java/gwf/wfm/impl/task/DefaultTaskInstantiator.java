package gwf.wfm.impl.task;

import gwf.api.WorkflowManagerException;
import gwf.api.task.WorkflowTask;
import gwf.api.task.instance.TaskConstructor;
import gwf.api.task.instance.TaskInstantiator;
import gwf.api.workflow.context.WorkflowContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public class DefaultTaskInstantiator implements TaskInstantiator {

	@Override
	public <T extends WorkflowTask> T create(Class<T> clazz) {

		Constructor<T> ctor = getConstructor(clazz);

		Object[] args = resolveArgs(ctor.getParameters());

		try {
			return ctor.newInstance(args);
		} catch(ReflectiveOperationException | RuntimeException e) {
			throw new WorkflowManagerException(String.format("Failed to instantiate %s", clazz), e);
		}
	}

	private Object[] resolveArgs(Parameter[] parameters) {

		Object[] resolved = new Object[parameters.length];

		for (int i = 0; i < resolved.length; i++) {
			resolved[i] = WorkflowContext.get(parameters[i].getType());
		}

		return resolved;
	}

	private <T> Constructor<T> getConstructor(Class<T> clazz) {

		Constructor<T> noArgConstructor = null;
		Constructor<T> annotatedConstructor = null;

		for(Constructor<?> ctor : clazz.getConstructors()) {

			if (ctor.getParameterCount() == 0) {
				noArgConstructor = (Constructor<T>) ctor;
				continue;
			}

			if (ctor.getAnnotation(TaskConstructor.class) != null) {
				if (annotatedConstructor != null) {
					throw new WorkflowManagerException(String.format("Multiple annotated constructors not supported on %s", clazz));
				}
				annotatedConstructor = (Constructor<T>) ctor;
			}
		}

		if (noArgConstructor == null && annotatedConstructor == null) {
			throw new WorkflowManagerException(String.format("No supported constructor found on %s", clazz));
		} else if (annotatedConstructor != null) {
			return annotatedConstructor;
		}
		return noArgConstructor;
	}
}
