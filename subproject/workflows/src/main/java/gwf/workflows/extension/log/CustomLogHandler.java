package gwf.workflows.extension.log;

import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CustomLogHandler extends Handler {

    private List<Consumer<LogRecord>> logConsumers = new ArrayList<>();

    public CustomLogHandler() {
        LogMessageProvider.register(this);
    }

    @Override
    public void publish(LogRecord record) {
        val it = logConsumers.iterator();

        while(it.hasNext()) {
            try {
                it.next().accept(record);
            } catch (Exception e) {
                it.remove();
            }
        }
    }

    public void register(Consumer<LogRecord> consumer) {
        List<Consumer<LogRecord>> newConsumers = new ArrayList<>(logConsumers);
        newConsumers.add(consumer);
        logConsumers = newConsumers;
    }

    @Override
    public void flush() {
        // nothing to flush here
    }

    @Override
    public void close() {
        // nothing to close
    }
}
