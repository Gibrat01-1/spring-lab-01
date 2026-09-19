package kz.iitu.springlab.notify;

import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("masking")
@Order(3)
public class MaskingNotifier implements Notifier {

    @PostConstruct
    public void init() {
        System.out.println("CUSTOM NOTIFIER >> MaskingNotifier initialized");
    }

    @Override
    public String send(String message) {
        return "MASKED: " + message.replaceAll("\\d", "*");
    }

    @Override
    public String channel() {
        return "masking";
    }
}