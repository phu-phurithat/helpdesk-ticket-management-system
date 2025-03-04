package com.phu.ticket_service.ticket;

import com.phu.ticket_service.handler.TicketNotFoundException;
import com.phu.ticket_service.ticket.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketResponse createTicket(TicketRequest request){
        Ticket ticket = ticketMapper.toTicket(request);
        ticket.setStatus(Status.PENDING);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(ticket);
    }

    public Page<TicketResponse> findTicketsPageSortFilter(String sortBy, String sortDirection, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        if (status != null) {
            return ticketRepository.findByStatus(
                    Status.valueOf(status.toUpperCase()), pageable)
                    .map(ticketMapper::toTicketResponse);
        }
        return ticketRepository.findAll(pageable)
                .map(ticketMapper::toTicketResponse);
    }

    public TicketResponse updateTicket(Long id, TicketUpdateRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setContact(request.getContact());
        ticket.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toTicketResponse(ticket);
    }


    public TicketResponse updateTicketStatus(Long id, TicketUpdateStatusRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        String status = request.getStatus().toUpperCase();
        ticket.setStatus(Status.valueOf(status));
        return ticketMapper.toTicketResponse(ticketRepository.save(ticket));
    }
}
