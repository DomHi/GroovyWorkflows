package gwf.workflows.ui.component.log;

import lombok.Value;

@Value
public class LogEntry {

    String level;

    String message;

    long millis;
}
