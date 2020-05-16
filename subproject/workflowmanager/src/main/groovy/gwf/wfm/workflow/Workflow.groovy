package gwf.wfm.workflow

import org.codehaus.groovy.control.CompilerConfiguration

class Workflow {

    private WorkflowScript script

    Workflow(URI source) {
        loadScript(source)
    }

    void run(delegate) {
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
