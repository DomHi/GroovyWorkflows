package gwf.wfm.workflow

import gwf.api.workflow.WorkflowExecutionContext

class WorkflowExecutionContextImpl implements WorkflowExecutionContext {

    private String wfName;
    private String release;
    private String swVersion;
    private String technology;

    WorkflowExecutionContextImpl(String wfName) {
        this(wfName, null, null, null)
    }

    WorkflowExecutionContextImpl(String wfName, String release, String swVersion, String technology) {
        this.wfName = wfName
        this.release = release
        this.swVersion = swVersion
        this.technology = technology
    }

    @Override
    String getWorkflowName() {
        return wfName
    }

    @Override
    String getRelease() {
        release
    }

    @Override
    String getSwVersion() {
        swVersion
    }

    @Override
    String getTechnology() {
        technology
    }
}
