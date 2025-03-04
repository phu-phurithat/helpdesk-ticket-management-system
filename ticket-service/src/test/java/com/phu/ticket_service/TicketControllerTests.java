package com.phu.ticket_service;

import com.phu.ticket_service.ticket.TicketController;
import com.phu.ticket_service.ticket.TicketService;
import com.phu.ticket_service.ticket.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketControllerTests {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private MockMvc mockMvc;
    private TicketRequest ticketRequest;
    private TicketResponse ticketResponse;
    private TicketUpdateRequest ticketUpdateRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketRequest = new TicketRequest();
        ticketRequest.setTitle("New Ticket");
        ticketRequest.setDescription("This is a test ticket");
        ticketRequest.setContact("contact@example.com");

        ticketResponse = new TicketResponse();
        ticketResponse.setId(1L);
        ticketResponse.setTitle("New Ticket");
        ticketResponse.setDescription("This is a test ticket");
        ticketResponse.setContact("contact@example.com");
        ticketResponse.setStatus(Status.PENDING);

        ticketUpdateRequest = new TicketUpdateRequest();
        ticketUpdateRequest.setTitle("Updated Ticket");
        ticketUpdateRequest.setDescription("Updated description");
        ticketUpdateRequest.setContact("contact@example.com");
        ticketUpdateRequest.setStatus("ACCEPTED");
    }

    @Test
    void givenTicketRequest_whenCreateTicket_thenReturnTicketResponse() throws Exception {
        when(ticketService.createTicket(any(TicketRequest.class))).thenReturn(ticketResponse);

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Ticket\", \"description\":\"This is a test ticket\" ,\"contact\":\"contact@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Ticket"));

        verify(ticketService, times(1)).createTicket(any(TicketRequest.class));
    }

    @Test
    void givenTicketRequest_whenFindTickets_thenReturnTicketResponse() throws Exception {
        Page<TicketResponse> page = new PageImpl<>(List.of(ticketResponse), PageRequest.of(0, 10, Sort.by("lastUpdate")), 1);
        when(ticketService.findTicketsPageSortFilter(any(), any(), any(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("New Ticket"));

        verify(ticketService, times(1)).findTicketsPageSortFilter(any(), any(), any(), anyInt(), anyInt());
    }

    @Test
    void givenTicketRequest_whenUpdateTicket_thenReturnTicketResponse() throws Exception {
        TicketResponse updatedResponse = new TicketResponse();
        updatedResponse.setId(1L);
        updatedResponse.setTitle("Updated Ticket");
        updatedResponse.setDescription("Updated description");
        updatedResponse.setContact("contact@example.com");
        updatedResponse.setStatus(Status.ACCEPTED);
        when(ticketService.updateTicket(anyLong(), any(TicketUpdateRequest.class))).thenReturn(updatedResponse);

        mockMvc.perform(put("/api/v1/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Ticket\", \"description\":\"Updated description\", \"contact\":\"contact@example.com\", \"status\":\"ACCEPTED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Ticket"))
                .andExpect(jsonPath("$.status").value("ACCEPTED"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.contact").value("contact@example.com"));

        verify(ticketService, times(1)).updateTicket(anyLong(), any(TicketUpdateRequest.class));
    }

    @Test
    void givenTicketId_whenUpdateTicketStatus_thenReturnTicketResponse() throws Exception {
        TicketResponse ticketResponse = TicketResponse.builder()
                .id(1L)
                .title("New Ticket")
                .description("This is a test ticket")
                .contact("contact@example.com")
                .status(Status.ACCEPTED)
                .build();
        TicketUpdateStatusRequest request = new TicketUpdateStatusRequest("ACCEPTED");
        when(ticketService.updateTicketStatus(1L, request)).thenReturn(ticketResponse);

        mockMvc.perform(put("/api/v1/tickets/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"ACCEPTED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Ticket"))
                .andExpect(jsonPath("$.status").value("ACCEPTED"));

        verify(ticketService, times(1)).updateTicketStatus(1L, request);
    }
}
