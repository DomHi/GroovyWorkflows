package gwf.util.task.sql;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;

public class SimpleStatement extends AbstractJdbiStatement {

    private String stmt = null;

    public void setStatement(String stmt) {
        if (this.stmt == null) {
            this.stmt = stmt;
        }
    }

    @Override
    protected void execute(Jdbi jdbi) {
        jdbi.useHandle(
                handle -> {
                    Update u = handle.createUpdate(stmt);
                    getDefine().forEach(u::define);
                    getBind().forEach(u::bind);
                    u.execute();
                }
        );
    }
}
