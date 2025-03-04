package com.phu.ticket_service;

import com.phu.ticket_service.ticket.TicketRepository;
import com.phu.ticket_service.ticket.model.Status;
import com.phu.ticket_service.ticket.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TicketRepositoryTests {

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket ticket;


    @BeforeEach
    public void setupTestData() {
        // Given : Setup object or precondition
        ticket = Ticket.builder()
                .title("Test Ticket")
                .description("This is a test ticket")
                .contact("john.doe@example.com")
                .status(Status.PENDING)
                .build();
    }

    // JUnit Test for save employee operation
    @Test
    @DisplayName("JUnit test for save ticket operation")
    public void givenTicketObject_whenSave_thenReturnSavedTicket() {


        // When
        Ticket saveTicket = ticketRepository.save(ticket);

        // Then

        assertThat(saveTicket).isNotNull();
        assertThat(saveTicket.getId()).isGreaterThan(0);

    }

    // JUnit test for get Employee List operation
    @Test
    @DisplayName("JUnit test for get ticket List")
    public void givenTicketList_whenFindAll_thenTicketList() {

        // Given : Setup object or precondition
        Ticket ticketOne = Ticket.builder()
                .title("Test Ticket One")
                .description("This is a test ticket One")
                .contact("john.doe@example.com")
                .status(Status.PENDING)
                .build();

        Ticket ticketTwo = Ticket.builder()
                .title("Test Ticket Two")
                .description("This is a test ticket Two")
                .contact("john.doe@example.com")
                .status(Status.ACCEPTED)
                .build();

        ticketRepository.save(ticketOne);
        ticketRepository.save(ticketTwo);

        // When : Action of behavious that we are going to test
        List<Ticket> employees = ticketRepository.findAll();

        // Then : Verify the output
        assertThat(employees).isNotEmpty();
        assertThat(employees.size()).isEqualTo(2);
    }

    // JUnit test for get Employee By Id operation

    @Test
    @DisplayName("JUnit test for get ticket By Id")
    public void givenTicketObject_whenFindById_thenReturnTicketObject() {
        // Given : Setup object or precondition
        ticketRepository.save(ticket);

        // When : Action of behavious that we are going to test
        Ticket getTicket = ticketRepository.findById(ticket.getId()).get();

        // Then : Verify the output
        assertThat(getTicket).isNotNull();
    }


    // JUnit test for get employee update operation
    @Test
    @DisplayName("JUnit test for get ticket update operation")
    public void givenTicketObject_whenUpdate_thenTicketObject() {

        // Given: Setup object or precondition

        ticketRepository.save(ticket);

        // When: Action or behavior that we are going to test
        Ticket getTicket = ticketRepository.findById(ticket.getId()).get();

        getTicket.setTitle("Update Ticket");
        getTicket.setDescription("Update Description");
        getTicket.setStatus(Status.REJECTED);
        getTicket.setContact("update@gmail.com");

        Ticket updatedTicket = ticketRepository.save(getTicket);

        // Then: Verify the output or expected result
        assertThat(updatedTicket).isNotNull();
        assertThat(updatedTicket.getContact()).isEqualTo("update@gmail.com");
    }

}