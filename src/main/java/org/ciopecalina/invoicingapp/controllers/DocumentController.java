package org.ciopecalina.invoicingapp.controllers;

import org.ciopecalina.invoicingapp.services.WordDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DocumentController {

    @Autowired
    private WordDocumentService wordService;

    //http://localhost:8080/download-document?invoiceSeries=DEV&invoiceNo=12345
    @GetMapping("/download-document")
    public ResponseEntity<byte[]> downloadDocument(@RequestParam String invoiceSeries, @RequestParam String invoiceNo) throws IOException {
        String wordFilePath = wordService.createDocument(invoiceSeries, invoiceNo);

        if (wordFilePath == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        File wordFile = new File(wordFilePath);
        byte[] fileContent = Files.readAllBytes(wordFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.inline().filename(wordFile.getName()).build());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
