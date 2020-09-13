package gwf.workflows.data;

import java.util.Arrays;

public class YamlData {

	private String name;

	private String[] values;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "YamlData{" +
				"name='" + name + '\'' +
				", values=" + Arrays.toString(values) +
				'}';
	}
}
