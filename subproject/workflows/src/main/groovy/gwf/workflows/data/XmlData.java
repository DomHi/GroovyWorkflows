package gwf.workflows.data;

public class XmlData {

	private String name;

	private String value;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "XmlData{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
