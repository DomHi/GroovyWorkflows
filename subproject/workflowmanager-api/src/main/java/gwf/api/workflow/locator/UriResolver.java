package gwf.api.workflow.locator;

import java.net.URI;

public class UriResolver {

    private UriResolver() {}

    public static URI relative(URI source, String path) {
        return "jar".equals(source.getScheme()) ? relativeJarUri(source, path) : source.resolve(path);
    }

    private static URI relativeJarUri(URI uri, String path) {
        String source = uri.toString();

        int idx = source.indexOf("!");
        String jar = source.substring(0, idx + 1);
        String fileInJar = source.substring(idx + 1);

        String resolvedFileInJar = URI.create(fileInJar).resolve(path).toString();
        return URI.create(jar + resolvedFileInJar);
    }
}
