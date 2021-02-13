package gwf.workflows.extension.log;

import gwf.workflows.ui.component.log.LogEntry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.logging.LogRecord;

public class LogMessageProvider {

    private static final Sinks.Many<LogEntry> logSink = Sinks.many().multicast().directBestEffort();

    private LogMessageProvider() {}

    static void register(CustomLogHandler handler) {
        handler.register(LogMessageProvider::process);
    }

    private static void process(LogRecord record) {
        logSink.emitNext(convertToEntry(record), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    private static LogEntry convertToEntry(LogRecord record) {
        return new LogEntry(record.getLevel().getName(), record.getMessage());
    }

    public static Flux<LogEntry> listen() {
        return logSink.asFlux();
    }
}
