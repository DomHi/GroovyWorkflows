package gwf.wfm.impl.file.loader;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import gwf.api.WorkflowManagerException;

import java.net.URL;

public class YamlLoader<T> implements FileLoader<T> {

	private final Class<T> clazz;

	protected YamlLoader(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T load(URL url) {
		try {
			return new YAMLMapper().readValue(url, clazz);
		} catch (Exception e) {
			throw new WorkflowManagerException(String.format("Failed to read %s as %s", url, clazz), e);
		}
	}
}
