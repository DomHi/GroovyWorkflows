package gwf.wfm.impl.task;

import gwf.api.task.WorkflowTask;
import gwf.api.task.instance.TaskInstantiator;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

public class CdiTaskInstantiator implements TaskInstantiator {

	@Override
	public <T extends WorkflowTask> T create(Class<T> clazz) {
		Instance<T> instance = CDI.current().select(clazz);

		if(instance.isUnsatisfied()) {
			throw new IllegalArgumentException("Invalid " + clazz.toString());
		}
		return instance.get();
	}
}
