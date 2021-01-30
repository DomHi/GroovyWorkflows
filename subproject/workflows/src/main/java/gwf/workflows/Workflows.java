package gwf.workflows;

import gwf.workflows.ui.DemoUI;

import java.net.URI;

public class Workflows {

    private Workflows() {}

    public static URI[] getProvidedWorkflows() {
        URI[] uris = new URI[1];
        uris[0] = getResource("myTest.wfl");
        return uris;
    }

    private static URI getResource(String name) {
        try {
            return DemoUI.class.getClassLoader()
                    .getResource("workflows/" + name)
                    .toURI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
