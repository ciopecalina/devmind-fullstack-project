package org.ciopecalina.invoicingapp.controllers;

import org.ciopecalina.invoicingapp.services.WordDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DocumentController {

    @Autowired
    private WordDocumentService wordService;

    //http://localhost:8080/download-document?id=2
    @GetMapping("/download-document/{id}")
    public ResponseEntity<byte[]> downloadDocument( @PathVariable Integer id) throws IOException {

        String wordFilePath = wordService.createDocument(id);

        if (wordFilePath == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        File wordFile = new File(wordFilePath);
        byte[] fileContent = Files.readAllBytes(wordFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(wordFile.getName()).build());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

}
