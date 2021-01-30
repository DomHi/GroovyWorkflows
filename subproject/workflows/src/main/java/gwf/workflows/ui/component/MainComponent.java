package gwf.workflows.ui.component;

import gwf.workflows.WorkflowHelper;
import gwf.workflows.Workflows;
import gwf.workflows.ui.component.selector.WorkflowSelector;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

@Slf4j
public class MainComponent extends JPanel {

    private WorkflowSelector workflowSelector;
    private JButton runButton;

    public MainComponent() {
        super(new BorderLayout());

        addWorkflowSelector();
        addRunButton();
    }

    private void addWorkflowSelector() {
        workflowSelector = new WorkflowSelector(Workflows.getProvidedWorkflows());
        add(workflowSelector, BorderLayout.CENTER);
    }

    private void addRunButton() {
        runButton = new JButton("Run");
        addRunListener(runButton);
        add(runButton, BorderLayout.LINE_END);
    }

    private void addRunListener(JButton button) {
        actionsOf(button)
                .map(e -> workflowSelector.getCurrentSelection())
                .publishOn(Schedulers.boundedElastic())
                .subscribe(
                    this::runWorkflow,
                        e -> log.error("Error in workflow execution.", e)
                );
    }

    private void runWorkflow(URI uri) {
        setComponentsEnabled(false);
        try {
            WorkflowHelper.start(uri.toString());
        } finally {
            setComponentsEnabled(true);
        }
    }

    private void setComponentsEnabled(boolean enabled) {
        workflowSelector.setEnabled(enabled);
        runButton.setEnabled(enabled);
    }

    private Flux<ActionEvent> actionsOf(JButton button) {
        return Flux.create(sink -> {
            ActionListener listener = sink::next;

            button.addActionListener(listener);
            sink.onDispose(
                    () -> button.removeActionListener(listener)
            );

        }, FluxSink.OverflowStrategy.LATEST);
    }
}
