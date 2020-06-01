package gwf.api.util;

import groovy.lang.Closure;

@SuppressWarnings("java:S1452") // suppresses SONAR warning about returning generic wildcard types
public class ClosureUtil {

	private ClosureUtil() {}

	public static Closure<?> delegateFirst(Closure<?> cl) {
		return withStrategy(cl, Closure.DELEGATE_FIRST);
	}

	public static Closure<?> withStrategy(Closure<?> cl, int strategy) {
		Closure<?> clone = (Closure<?>) cl.clone();
		clone.setResolveStrategy(strategy);
		return clone;
	}
}
