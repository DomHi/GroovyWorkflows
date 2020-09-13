package gwf.wfm.impl.file.loader;

import gwf.api.WorkflowManagerException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PlainTextLoader implements FileLoader<String> {

	@Override
	public String load(URL url) {
		try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
			StringBuilder out = new StringBuilder();
			int bytes = -1;
			char[] buf = new char[1024];
			while ((bytes = reader.read(buf)) != -1) {
				out.append(buf, 0, bytes);
			}
			return out.toString();
		} catch (IOException e) {
			throw new WorkflowManagerException("Failed to read " + url, e);
		}
	}
}
