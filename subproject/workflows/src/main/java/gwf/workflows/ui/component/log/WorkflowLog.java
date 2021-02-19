package gwf.workflows.ui.component.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
public class WorkflowLog extends JPanel {

    private final DefaultListModel<LogEntry> logEntries = new DefaultListModel<>();

    public WorkflowLog() {
        super(new GridBagLayout());

        addLogArea();
        addClearButton();
    }

    private void addLogArea() {

        JList<LogEntry> entryList = new JList<>(logEntries);
        entryList.setCellRenderer(new LogListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(entryList, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        add(scrollPane, c);
    }

    private void addClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridy = 1;

        addClearAction(clearButton);

        add(clearButton, c);
    }

    private void addClearAction(JButton clearButton) {
        clearButton.addActionListener(
                e -> logEntries.removeAllElements()
        );
    }

    public void addLogEntry(LogEntry entry) {
        logEntries.addElement(entry);
    }

    private static class LogListCellRenderer implements ListCellRenderer<LogEntry> {

        private final JPanel panel = new JPanel();
        private final JTextField level = new JTextField();
        private final JTextField message = new JTextField();
        private final JTextField timestamp = new JTextField();

        private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss,SSS")
                .withZone(ZoneId.systemDefault());

        private LogListCellRenderer() {
            panel.setLayout(new GridBagLayout());

            initTimestampField();
            initLevelField();
            initMessageField();
        }

        private void initLevelField() {
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_START;

            Dimension lvlDim = level.getPreferredSize();
            lvlDim.setSize(100, lvlDim.getHeight());
            level.setPreferredSize(lvlDim);
            level.setHorizontalAlignment(SwingConstants.CENTER);
            level.setBorder(BorderFactory.createEmptyBorder());

            panel.add(level, c);
        }

        private void initMessageField() {
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;

            message.setBorder(BorderFactory.createEmptyBorder());

            panel.add(message, c);
        }

        private void initTimestampField() {
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_START;

            Dimension lvlDim = timestamp.getPreferredSize();
            lvlDim.setSize(100, lvlDim.getHeight());
            timestamp.setPreferredSize(lvlDim);
            timestamp.setHorizontalAlignment(SwingConstants.CENTER);
            timestamp.setBorder(BorderFactory.createEmptyBorder());

            panel.add(timestamp, c);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends LogEntry> list, LogEntry value, int index, boolean isSelected, boolean cellHasFocus) {

            LineColors colors = getColors(value, cellHasFocus);

            timestamp.setText(
                    timeFormatter.format(
                        Instant.ofEpochMilli(value.getMillis())
                    )
            );
            timestamp.setBackground(colors.getBackground());
            timestamp.setForeground(colors.getForeground());

            level.setText(value.getLevel());
            level.setBackground(colors.getBackground());
            level.setForeground(colors.getForeground());

            message.setText(value.getMessage());
            message.setBackground(colors.getBackground());
            message.setForeground(colors.getForeground());

            return panel;
        }

        private LineColors getColors(LogEntry entry, boolean cellHasFocus) {

            if (cellHasFocus) {
                return new LineColors(Color.BLUE, Color.WHITE);
            }

            switch (entry.getLevel()) {
                case "ERROR":
                    return new LineColors(Color.RED, Color.BLACK);
                case "WARN":
                    return new LineColors(Color.YELLOW, Color.BLACK);
                case "TRACE":
                    return new LineColors(Color.CYAN, Color.BLACK);
                default:
                    return new LineColors(Color.WHITE, Color.BLACK);
            }
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class LineColors {
        private final Color background;
        private final Color foreground;
    }
}
