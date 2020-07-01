package gwf.wfm.impl.workflow;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import gwf.api.WorkflowManagerException;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.IOException;
import java.net.URI;

public class ScriptLoader {

    public static WorkflowScript load(URI location) {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(WorkflowScript.class.getName());

        GroovyShell shell = new GroovyShell(new Binding(), cc);
        try {
            return (WorkflowScript) shell.parse(location);
        } catch (IOException e) {
            throw new WorkflowManagerException("Failed to parse script.", e);
        }
    }
}
