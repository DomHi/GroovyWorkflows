package gwf.wfm.workflow

import gwf.api.delegate.WorkflowDelegateBase
import org.codehaus.groovy.control.CompilerConfiguration

class Workflow {

    private WorkflowScript script

    Workflow(URI source) {
        loadScript(source)
    }

    void run(WorkflowDelegateBase delegate) {
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
