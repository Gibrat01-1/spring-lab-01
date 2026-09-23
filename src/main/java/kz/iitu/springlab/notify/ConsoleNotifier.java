package kz.iitu.springlab.notify;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("console")
@Order(1)
public class ConsoleNotifier implements Notifier {

    @Override
    public String send(String message) {
        return "CONSOLE: " + message;
    }

    @Override
    public String channel() {
        return "console";
    }
}