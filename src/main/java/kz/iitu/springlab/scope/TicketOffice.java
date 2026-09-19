package kz.iitu.springlab.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Component
public class TicketOffice {

    private final Ticket directTicket;
    private final ObjectProvider<Ticket> ticketProvider;

    public TicketOffice(
            Ticket directTicket,
            ObjectProvider<Ticket> ticketProvider) {

        this.directTicket = directTicket;
        this.ticketProvider = ticketProvider;
    }

    public String directTicketId() {
        return directTicket.getId();
    }

    public String providerTicketId() {
        return ticketProvider.getObject().getId();
    }

    public String officeId() {
        return Integer.toHexString(System.identityHashCode(this));
    }
}