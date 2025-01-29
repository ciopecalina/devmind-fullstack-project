package org.ciopecalina.invoicingapp.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class SendGridService {
    @Value("${sendgrid.SENDGRID_API_KEY}")
    private String SENDGRID_API_KEY;

    public void sendEmail(String fromStr, String toStr, String subjectStr, String contentStr, String filePath) throws IOException {
        Email from = new Email(fromStr);
        Email to = new Email(toStr);
        Content content = new Content("text/plain", contentStr);
        Mail mail = new Mail(from, subjectStr, to, content);

        if (filePath != null && !filePath.isEmpty()) {
            Path file = Path.of(filePath);
            Attachments attachments = new Attachments();
            attachments.setFilename(file.getFileName().toString());
            attachments.setType("application/pdf");
            attachments.setDisposition("attachment");
            byte[] attachmentContentBytes = Files.readAllBytes(file);
            String attachmentContent = Base64.getMimeEncoder().encodeToString(attachmentContentBytes);
            attachments.setContent(attachmentContent);
            mail.addAttachments(attachments);
        }

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println("Status cod: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        } catch (IOException ex) {
            throw new IOException("Faild to send email: " + ex.getMessage());
        }
    }
}

