package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.services.SendGridService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class SendGridController {
    private final SendGridService sendGridService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam Integer invoiceId, @RequestParam String clientName) {
        try {
            sendGridService.sendEmail(invoiceId, clientName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Emails sent successfully");
    }

}