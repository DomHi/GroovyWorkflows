package gwf.workflows.ui.component;

import gwf.workflows.extension.log.LogMessageProvider;
import gwf.workflows.ui.component.log.WorkflowLog;
import gwf.workflows.ui.component.stats.StatisticsComponent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainComponent extends JPanel {

    private TopBar topBar;
    private WorkflowLog workflowLog;
    private StatisticsComponent statisticsComponent;

    public MainComponent() {
        super(new GridBagLayout());

        addTopBar();
        addLogComponent();
        addStatisticsComponent();
    }

    private void addTopBar() {
        topBar = new TopBar();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        add(topBar, c);
    }

    private void addLogComponent() {
        workflowLog = new WorkflowLog();
        workflowLog.setBorder(
                BorderFactory.createEmptyBorder(5,0,0,0)
        );

        LogMessageProvider.listen()
                .subscribe(workflowLog::addLogEntry);

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        add(workflowLog, c);
    }

    private void addStatisticsComponent() {
        statisticsComponent = new StatisticsComponent();

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;

        add(statisticsComponent, c);
    }
}
