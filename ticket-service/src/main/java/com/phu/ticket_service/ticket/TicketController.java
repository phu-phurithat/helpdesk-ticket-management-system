package com.phu.ticket_service.ticket;

import com.phu.ticket_service.ticket.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Ticket API", description = "Operations related to tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket", description = "Creates a new ticket and returns the created ticket details.")
    @ApiResponse(responseCode = "201", description = "Ticket created successfully",
            content = @Content(schema = @Schema(implementation = TicketResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request payload")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody @Valid TicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
    }

    @GetMapping
    @Operation(summary = "Get all tickets", description = "Retrieves a paginated list of tickets with optional sorting and filtering.")
    @ApiResponse(responseCode = "200", description = "List of tickets retrieved successfully",
            content = @Content(schema = @Schema(implementation = Page.class)))
    public ResponseEntity<Page<TicketResponse>> getAllTickets(
            @RequestParam(defaultValue = "lastUpdate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(ticketService.findTicketsPageSortFilter(sortBy, direction, status, page, pageSize));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a ticket", description = "Updates the details of an existing ticket.")
    @ApiResponse(responseCode = "200", description = "Ticket updated successfully",
            content = @Content(schema = @Schema(implementation = TicketResponse.class)))
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody @Valid TicketUpdateRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update ticket status", description = "Updates the status of a specific ticket.")
    @ApiResponse(responseCode = "200", description = "Ticket status updated successfully",
            content = @Content(schema = @Schema(implementation = TicketResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    @ApiResponse(responseCode = "404", description = "Ticket not found")
    public ResponseEntity<TicketResponse> updateTicketStatus(@PathVariable Long id, @RequestBody TicketUpdateStatusRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, request));
    }
}
