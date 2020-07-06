package gwf.util.task.sql;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import gwf.api.util.ClosureUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJdbiStatement extends Statement {

    private final Map<String, Object> binding = new HashMap<>();
    private final Map<String, Object> definitions = new HashMap<>();

    public Map<String, Object> getBind() {
        return binding;
    }

    public Map<String, Object> getDefine() {
        return definitions;
    }

    public void bind(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl) {
        ClosureUtil.delegateFirst(cl, binding).call();
    }

    public void define(@DelegatesTo(strategy = Closure.DELEGATE_FIRST, type = "Map<String,Object>") Closure<?> cl) {
        ClosureUtil.delegateFirst(cl, definitions).call();
    }
}
