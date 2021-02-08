package gwf.workflows.ui.component.log;

import javax.swing.*;
import java.awt.*;

public class WorkflowLog extends JPanel {

    private JTextArea textArea;
    private JButton clearButton;

    public WorkflowLog() {
        super(new GridBagLayout());

        addTextArea();
        addClearButton();
    }

    private void addTextArea() {
        textArea = new JTextArea();
        textArea.setEditable(false);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        add(textArea, c);
    }

    private void addClearButton() {
        clearButton = new JButton("Clear");
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridy = 1;

        addClearAction(clearButton);

        add(clearButton, c);
    }

    private void addClearAction(JButton clearButton) {
        clearButton.addActionListener(
                e -> textArea.replaceRange(null, 0, textArea.getDocument().getLength())
        );
    }
}
