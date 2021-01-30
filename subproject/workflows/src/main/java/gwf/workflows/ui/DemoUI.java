package gwf.workflows.ui;

import gwf.workflows.ui.component.MainComponent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class DemoUI {

    private DemoUI() {}

    public static void start() {

        setLookAndFeel();

        JFrame mainFrame = new JFrame("DemoUI");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.add(new MainComponent());

        mainFrame.setPreferredSize(new Dimension(300, 200));

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private static void setLookAndFeel() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.error("Failed to set look and feel.", e);
        }
    }
}
