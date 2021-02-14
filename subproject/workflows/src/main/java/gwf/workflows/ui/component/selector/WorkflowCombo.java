package gwf.workflows.ui.component.selector;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class WorkflowCombo extends JComboBox<URI> {

    MutableComboBoxModel<URI> model;

    public WorkflowCombo(URI[] initialValues) {
        super();
        setRenderer(new WorkflowCellRenderer());
        initModel(initialValues);
    }

    private void initModel(URI[] initialValues) {
        model = new DefaultComboBoxModel<>(initialValues);
        setModel(model);
    }

    public URI getSelected() {
        return (URI) model.getSelectedItem();
    }

    public void addWorkflow(URI uri) {
        for (int i = 0; i < model.getSize(); i++) {
            if (model.getElementAt(i).equals(uri)) {
                return; // no need to add URI a second time
            }
        }
        model.addElement(uri);
    }

    @Slf4j
    private static final class WorkflowCellRenderer implements ListCellRenderer<URI> {

        @Override
        public Component getListCellRendererComponent(JList<? extends URI> list, URI value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel uriLabel = new JLabel();
            uriLabel.setText(getUriHtml(value));
            return uriLabel;
        }

        private String getUriHtml(URI uri) {
            String[] path = uri.toString().split("/");
            return String.format("<html>%s <font color=\"#aaaaaa\">-- %s</font></html>", path[path.length - 1], uri.getScheme());
        }
    }
}
