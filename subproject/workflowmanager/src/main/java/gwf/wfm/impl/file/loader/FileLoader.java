package gwf.wfm.impl.file.loader;

import gwf.api.WorkflowManagerException;

import java.net.URL;

public interface FileLoader<T> {

	T load(URL url);

	static <T> FileLoader<T> of(String extension, Class<T> type) {
		if (String.class == type) {
			return (FileLoader<T>) new PlainTextLoader();
		} else if ("json".equalsIgnoreCase(extension)) {
			return new JsonLoader<>(type);
		} else if ("xml".equalsIgnoreCase(extension)) {
			return new XmlLoader<>(type);
		} else if ("yaml".equalsIgnoreCase(extension)) {
			return new YamlLoader<>(type);
		} else {
			throw new WorkflowManagerException(String.format("Unknown conversion for %s to %s.", extension, type));
		}
	}
}
