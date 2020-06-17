package gwf.wfm.impl.workflow;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import gwf.api.delegate.WorkflowDelegateBase;
import gwf.api.exception.WorkflowManagerException;
import gwf.api.workflow.WorkflowConfiguration;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.IOException;
import java.net.URI;

public class WorkflowConfigurationImpl implements WorkflowConfiguration {

	private WorkflowScript script;

	public WorkflowConfigurationImpl(URI source) {
		loadScript(source);
	}

	@Override
	public void configure(WorkflowDelegateBase delegate) {
		script.setDelegate(delegate);
		script.run();
	}

	private void loadScript(URI source) {
		CompilerConfiguration cc = new CompilerConfiguration();
		cc.setScriptBaseClass(WorkflowScript.class.getName());

		GroovyShell shell = new GroovyShell(new Binding(), cc);
		try {
			this.script = (WorkflowScript) shell.parse(source);
		} catch(IOException e) {
			throw new WorkflowManagerException("Failed to parse script.", e);
		}
	}
}
