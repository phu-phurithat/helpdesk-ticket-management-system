package com.phu.ticket_service;

import com.phu.ticket_service.handler.TicketNotFoundException;
import com.phu.ticket_service.ticket.TicketMapper;
import com.phu.ticket_service.ticket.TicketRepository;
import com.phu.ticket_service.ticket.TicketService;
import com.phu.ticket_service.ticket.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private TicketRequest ticketRequest;
    private TicketResponse ticketResponse;

    @BeforeEach
    void setUp() {
        ticket = Ticket.builder()
                .title("Issue")
                .description("Description")
                .contact("contact@example.com")
                .status(Status.PENDING)
                .build();
        ticketRequest = new TicketRequest("Issue", "Description", "contact@example.com");
        ticketResponse = TicketResponse.builder()
                .id(1L)
                .title("Issue")
                .description("Description")
                .contact("contact@example.com")
                .status(Status.PENDING)
                .build();
    }

    @Test
    void givenTicketRequest_whenCreateTicket_thenTicketResponse() {
        when(ticketMapper.toTicket(any(TicketRequest.class))).thenReturn(ticket);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toTicketResponse(any(Ticket.class))).thenReturn(ticketResponse);

        TicketResponse result = ticketService.createTicket(ticketRequest);

        assertNotNull(result);
        assertEquals("Issue", result.getTitle());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void givenTicketRequest_whenFindTicketsPageSortFilter_thenReturnTicketResponse() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title"));
        Page<Ticket> page = new PageImpl<>(Collections.singletonList(ticket));
        when(ticketRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(ticketMapper.toTicketResponse(any(Ticket.class))).thenReturn(ticketResponse);

        Page<TicketResponse> result = ticketService.findTicketsPageSortFilter("title", "ASC", null, 0, 5);

        assertFalse(result.isEmpty());
        verify(ticketRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void givenTicketRequest_whenUpdateTicket_thenReturnTicketResponse() {
        TicketUpdateRequest updateRequest = new TicketUpdateRequest("New Title", "New Description", "newcontact@example.com", "RESOLVED");
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toTicketResponse(any(Ticket.class))).thenReturn(ticketResponse);

        TicketResponse result = ticketService.updateTicket(1L, updateRequest);

        assertNotNull(result);
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void givenTicketId_whenUpdateTicketStatus_thenReturnTicketResponse() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);
        when(ticketMapper.toTicketResponse(any(Ticket.class))).thenReturn(ticketResponse);

        TicketUpdateStatusRequest request = new TicketUpdateStatusRequest("RESOLVED");
        TicketResponse result = ticketService.updateTicketStatus(1L, request);

        assertNotNull(result);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void givenInvalidTicketId_whenUpdateTicket_thenThrowTicketNotFoundException() {
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.updateTicket(1L, new TicketUpdateRequest("New Title", "New Description", "contact", "PENDING")));
        verify(ticketRepository, times(1)).findById(1L);
    }
}