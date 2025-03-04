package com.phu.ticket_service.ticket;

import com.phu.ticket_service.ticket.model.Status;
import com.phu.ticket_service.ticket.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByStatus(Status status, Pageable pageable);
}
