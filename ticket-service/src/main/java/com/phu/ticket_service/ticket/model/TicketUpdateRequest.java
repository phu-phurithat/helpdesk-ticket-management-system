package com.phu.ticket_service.ticket.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketUpdateRequest {

    @NotNull(message = "Title is required")
    private String title;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Contact is required")
    @Email(message = "Invalid email address")
    private String contact;
    @NotEmpty(message = "Status is required")
    @Pattern(regexp = "(?i)PENDING|ACCEPTED|RESOLVED|REJECTED", message = "Status must be PENDING, ACCEPTED, RESOLVED, REJECTED")
    private String status;
}
