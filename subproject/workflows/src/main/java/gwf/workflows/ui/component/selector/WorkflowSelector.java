package gwf.workflows.ui.component.selector;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;

@Slf4j
public class WorkflowSelector extends JPanel {

    WorkflowCombo workflowCombo;
    JButton addButton;

    JFileChooser fileChooser = new JFileChooser();

    public WorkflowSelector(URI[] initialValues) {
        super(new GridBagLayout());

        addWorkflowCombo(initialValues);
        addAddButton();
    }

    public URI getCurrentSelection() {
        return workflowCombo.getSelected();
    }

    private void addWorkflowCombo(URI[] initialValues) {
        workflowCombo = new WorkflowCombo(initialValues);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        add(workflowCombo, c);
    }

    private void addAddButton() {
        addButton = new JButton("Add");
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(this::addAction);
        add(addButton);
    }

    private void addAction(ActionEvent event) {
        askUserFile()
                .map(File::toURI)
                .subscribe(
                        workflowCombo::addWorkflow,
                        e -> log.error("Failed to add user provided workflow.", e)
                );
    }

    private Mono<File> askUserFile() {
        return Mono.fromSupplier( () -> {
                int result = fileChooser.showOpenDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    return fileChooser.getSelectedFile();
                } else {
                    return null;
                }
        })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSubscribe(s -> addButton.setEnabled(false))
                .doOnTerminate(() -> addButton.setEnabled(true));
    }
}
