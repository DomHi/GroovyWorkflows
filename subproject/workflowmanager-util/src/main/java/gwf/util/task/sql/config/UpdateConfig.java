package gwf.util.task.sql.config;

public interface UpdateConfig extends JdbiBindingProvider, JdbiDefinitionProvider {

	void setStatement(String stmt);

	String getStatement();
}
