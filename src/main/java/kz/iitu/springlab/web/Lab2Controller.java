package kz.iitu.springlab.web;

import kz.iitu.springlab.lifecycle.LifecycleDemo;
import kz.iitu.springlab.notify.NotificationService;
import kz.iitu.springlab.notify.Notifier;
import kz.iitu.springlab.scope.TicketOffice;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notificationService;
    private final LifecycleDemo lifecycleDemo;
    private final TicketOffice ticketOffice;
    private final Notifier maskingNotifier;

    public Lab2Controller(
            NotificationService notificationService,
            LifecycleDemo lifecycleDemo,
            TicketOffice ticketOffice,
            @Qualifier("masking") Notifier maskingNotifier) {

        this.notificationService = notificationService;
        this.lifecycleDemo = lifecycleDemo;
        this.ticketOffice = ticketOffice;
        this.maskingNotifier = maskingNotifier;
    }

    @GetMapping("/notify")
    public NotifyResponse notify(
            @RequestParam(defaultValue = "Hello") String text) {

        return new NotifyResponse(
                notificationService.primary(text),
                notificationService.console(text),
                notificationService.all(text),
                notificationService.map(text)
        );
    }

    @GetMapping("/lifecycle")
    public String lifecycle() {
        return "LifecycleDemo is active";
    }

    @GetMapping("/scopes")
    public ScopeResponse scopes() {
        return new ScopeResponse(
                ticketOffice.directTicketId(),
                ticketOffice.providerTicketId(),
                ticketOffice.providerTicketId(),
                ticketOffice.officeId()
        );
    }

    @GetMapping("/custom")
    public String custom(
            @RequestParam(defaultValue = "Hello 123") String text) {

        return maskingNotifier.send(text);
    }

    public record NotifyResponse(
            String primary,
            String console,
            java.util.List<String> all,
            java.util.Map<String, String> map
    ) {
    }

    public record ScopeResponse(
            String directTicketId,
            String providerTicketId1,
            String providerTicketId2,
            String officeId
    ) {
    }
}

