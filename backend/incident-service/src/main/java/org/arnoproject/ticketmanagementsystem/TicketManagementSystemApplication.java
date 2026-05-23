package org.arnoproject.ticketmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class TicketManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketManagementSystemApplication.class, args);
    }
    @GetMapping("/health")
    public String health() {
        return "Incident Service is running";
    }

}
