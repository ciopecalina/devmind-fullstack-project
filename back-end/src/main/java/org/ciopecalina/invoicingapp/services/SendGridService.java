package org.ciopecalina.invoicingapp.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class SendGridService {
    @Value("${sendgrid.SENDGRID_API_KEY}")
    private String SENDGRID_API_KEY;

    @Value("${sendgrid.SENDER}")
    private String SENDER;

    @Autowired
    private WordDocumentService wordDocumentService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;

    public void sendEmail(Integer id, String toStr) throws IOException {
        String wordFilePath = wordDocumentService.createDocument(id);

        Invoice invoice = invoiceService.getInvoiceById(id);
        String series = invoice.getSeries();
        String no = invoice.getNumber();
        User client = userService.getUserByName(toStr);

        Email from = new Email(SENDER);
        String subjectStr = "New invoice " + series + " - " + no;

        String contentStr = "You received a new invoice. Please find the attached file.";
        Email to = new Email(client.getEmail());
        Content content = new Content("text/plain", contentStr);

        Mail mail = new Mail(from, subjectStr, to, content);

        Path file = Paths.get(wordFilePath);
        Attachments attachments = new Attachments();
        attachments.setFilename(file.getFileName().toString());
        attachments.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        attachments.setDisposition("attachment");

        byte[] attachmentContentBytes = Files.readAllBytes(file);
        String attachmentContent = Base64.getMimeEncoder().encodeToString(attachmentContentBytes);
        attachments.setContent(attachmentContent);
        mail.addAttachments(attachments);

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

