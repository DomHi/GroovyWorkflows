package gwf.wfm.impl.file.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import gwf.api.WorkflowManagerException;

import java.net.URL;

public class JsonLoader<T> implements FileLoader<T> {

	private final Class<T> clazz;

	protected JsonLoader(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T load(URL url) {
		try {
			return new ObjectMapper().readValue(url, clazz);
		} catch (Exception e) {
			throw new WorkflowManagerException(String.format("Failed to read %s as %s", url, clazz), e);
		}
	}
}
