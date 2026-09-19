package kz.iitu.springlab.notify;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("email")
@Primary
@Order(2)
public class EmailNotifier implements Notifier {

    @Override
    public String send(String message) {
        return "EMAIL: " + message;
    }

    @Override
    public String channel() {
        return "email";
    }
}