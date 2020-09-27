package gwf.util.task.generic;

import groovy.lang.Closure;

public interface GenericConfig {

	void action(Closure<?> cl);

	String getName();
}
