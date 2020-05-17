package gwf.wfm.workflow

import gwf.api.delegate.WorkflowDelegateBase
import gwf.api.workflow.WorkflowConfiguration
import org.codehaus.groovy.control.CompilerConfiguration

class WorkflowConfigurationImpl implements WorkflowConfiguration {

    private WorkflowScript script

    WorkflowConfigurationImpl(URI source) {
        loadScript(source)
    }

    @Override
    void configure(WorkflowDelegateBase delegate) {
        script.setDelegate(delegate)
        script.run()
    }

    private void loadScript(URI source) {
        def cc = new CompilerConfiguration()
        cc.setScriptBaseClass(WorkflowScript.class.name)

        def shell = new GroovyShell(new Binding(), cc)
        this.script = (WorkflowScript) shell.parse(source)
    }
}
