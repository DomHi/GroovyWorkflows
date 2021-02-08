package gwf.workflows.ui;

import gwf.workflows.ui.component.MainComponent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class DemoUI {

    private DemoUI() {}

    public static void start() {

        // setLookAndFeel();

        JFrame mainFrame = new JFrame("DemoUI");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.add(new MainComponent());

        mainFrame.setPreferredSize(new Dimension(800, 600));
        mainFrame.pack();

        // call this after setting size and pack
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setVisible(true);
    }

    private static void setLookAndFeel() {

        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    log.warn("Failed to set Nimbus LaF.", e);
                }
            }
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.error("Failed to set look and feel.", e);
        }
    }
}
