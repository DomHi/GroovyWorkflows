package gwf.api.delegate;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.executor.ExecutorConfig;
import gwf.api.task.TaskContainer;
import org.slf4j.Logger;

import java.net.URL;
import java.util.Map;

/**
 * Base interface implemented by delegates of workflow scripts.
 * Provides utilities for logging, obtaining workflow context and adding tasks.
 */
public interface WorkflowDelegateBase {

	/**
	 * @return Logger for the current workflow context
	 */
	Logger getLogger();

	/**
	 * @return environment of workflow execution
	 */
	Map<String, String> getEnv();

	/**
	 * @param cl Closure used to configure executors
	 */
	void executor(@DelegatesTo(value = ExecutorConfig.class, strategy = Closure.DELEGATE_FIRST) Closure<?> cl);

	/**
	 * Configures the TaskConfig using the given Closure.
	 *
	 * @param config Closure which will be executed with the TaskConfig delegate
	 */
	void tasks(@DelegatesTo(value = TaskContainer.class, strategy = Closure.DELEGATE_FIRST) Closure<?> config);

	/**
	 * Inline workflow. This will add tasks of given workflow to the current execution.
	 *
	 * @param path of the workflow to inline. Will be resolved relative to current workflows location.
	 */
	void inline(String path);

	/**
	 * Resolves given {@code uri} against this workflows location. For details see {@link java.net.URI#resolve(String)}.
	 * After resolving the resulting {@link java.net.URI} is converted to {@link java.net.URL}.
	 *
	 * @param uri which will be resolved against workflow location
	 * @return resulting {@link java.net.URL}
	 */
	URL url(String uri);

	/**
	 * Load file as given {@code type}.
	 * When type is {@link java.lang.String String} the file will be read as plain text.
	 * To return a POJO the data must be in json, xml or yaml format (inferred by file extension).
	 *
	 * <p>
	 * For parsing POJOs <a href="https://github.com/FasterXML/jackson">Jackson</a> is used.
	 * So the given {@code type} should be compatible.
	 * </p>
	 *
	 * @param type of the returned object
	 * @param file to read (see {@link #url(String)})
	 * @return object created by loading given file
	 */
	<T> T load(Class<T> type, String file);

	/**
	 * Use {@link javax.enterprise.inject.spi.CDI CDI} to look up a bean instance.
	 *
	 * @param type of bean
	 * @return bean instance
	 */
	<T> T cdi(Class<T> type);
}
