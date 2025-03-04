package com.phu.ticket_service.ticket.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String contact;
    private Status status;
    private LocalDateTime createdDate;
    private LocalDateTime lastTicketUpdate;

}
