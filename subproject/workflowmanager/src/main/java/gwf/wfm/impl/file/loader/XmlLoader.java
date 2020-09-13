package gwf.wfm.impl.file.loader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import gwf.api.WorkflowManagerException;

import java.net.URL;

public class XmlLoader<T> implements FileLoader<T> {

	private final Class<T> clazz;

	protected XmlLoader(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T load(URL url) {
		try {
			return new XmlMapper().readValue(url, clazz);
		} catch (Exception e) {
			throw new WorkflowManagerException(String.format("Failed to read %s as %s", url, clazz), e);
		}
	}
}
