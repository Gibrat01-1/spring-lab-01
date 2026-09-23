package kz.iitu.springlab.notify;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final Notifier primaryNotifier;
    private final Notifier consoleNotifier;
    private final List<Notifier> allNotifiers;
    private final Map<String, Notifier> notifierMap;

    public NotificationService(
            Notifier primaryNotifier,
            @Qualifier("console") Notifier consoleNotifier,
            List<Notifier> allNotifiers,
            Map<String, Notifier> notifierMap) {

        this.primaryNotifier = primaryNotifier;
        this.consoleNotifier = consoleNotifier;
        this.allNotifiers = allNotifiers;
        this.notifierMap = notifierMap;
    }

    public String primary(String message) {
        return primaryNotifier.send(message);
    }

    public String console(String message) {
        return consoleNotifier.send(message);
    }

    public List<String> all(String message) {
        return allNotifiers.stream()
                .map(notifier -> notifier.send(message))
                .toList();
    }

    public Map<String, String> map(String message) {
        return notifierMap.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().send(message)
                ));
    }
}