package gwf.api.workflow.context;

public interface MergeableContext {

	MergeableContext merge(MergeableContext other);
}
