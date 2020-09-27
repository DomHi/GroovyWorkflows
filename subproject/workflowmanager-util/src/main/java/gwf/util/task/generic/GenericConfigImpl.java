package gwf.util.task.generic;

import groovy.lang.Closure;
import gwf.api.util.ClosureUtil;

import java.util.ArrayList;
import java.util.List;

public class GenericConfigImpl implements GenericConfig {

	private final String taskName;

	private final List<Closure<?>> taskActions = new ArrayList<>();

	protected GenericConfigImpl(String taskName) {
		this.taskName = taskName;
	}

	protected List<Closure<?>> getTaskActions() {
		return taskActions;
	}

	@Override
	public void action(Closure<?> cl) {
		taskActions.add(ClosureUtil.delegateFirst(cl));
	}

	@Override
	public String getName() {
		return taskName;
	}
}
