package com.phu.ticket_service.handler;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketNotFoundException extends RuntimeException{

    private final String message;
}
