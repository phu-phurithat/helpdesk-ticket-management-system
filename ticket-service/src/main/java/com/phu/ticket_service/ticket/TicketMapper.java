package com.phu.ticket_service.ticket;

import com.phu.ticket_service.ticket.model.Ticket;
import com.phu.ticket_service.ticket.model.TicketRequest;
import com.phu.ticket_service.ticket.model.TicketResponse;
import org.springframework.stereotype.Service;

@Service
public class TicketMapper {
    public Ticket toTicket(TicketRequest request) {
        return Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .contact(request.getContact())
                .build();
    }

    public TicketResponse toTicketResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .contact(ticket.getContact())
                .status(ticket.getStatus())
                .createdDate(ticket.getCreatedDate())
                .lastTicketUpdate(ticket.getLastUpdate())
                .build();
    }
}
