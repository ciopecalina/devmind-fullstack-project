package org.ciopecalina.invoicingapp.services;

import org.apache.poi.xwpf.usermodel.*;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.models.InvoiceProduct;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Set;

@Service
public class WordDocumentService {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;

    private static final String INVOICE_DIRECTORY = "src/main/resources/invoices/";
    private static final String TEMPLATE_PATH = "src/main/resources/templates/invoice-template.docx";

    public String createDocument(Integer id) {
        String wordPath = modifyWordTemplate(id);
        return wordPath;
    }

    public String modifyWordTemplate(Integer id) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        String outputPath = INVOICE_DIRECTORY + "Invoice_" + invoice.getSeries() + "-" + invoice.getNumber() + ".docx";

        try (InputStream resourceStream = new FileInputStream(TEMPLATE_PATH)) {
            XWPFDocument document = new XWPFDocument(resourceStream);

            // Invoice data
            findAndReplaceText(document, "<s>", invoice.getSeries());
            findAndReplaceText(document, "<no>", invoice.getNumber());
            findAndReplaceText(document, "<d>", String.valueOf(invoice.getDate()));
            findAndReplaceText(document, "<tnv>", String.valueOf(invoice.getTotalNoVat()));
            findAndReplaceText(document, "<tv>", String.valueOf(invoice.getTotalWithVat()));
            findAndReplaceText(document, "<t>", String.valueOf(invoice.getVat()));

            // User data
            User user = invoice.getUser();
            findAndReplaceText(document, "<u_name>", user.getName());
            findAndReplaceText(document, "<u_reg_no>", user.getRegNo());
            findAndReplaceText(document, "<u_iban>", user.getIban());
            findAndReplaceText(document, "<u_bank>", user.getBank());

            // Client data
            User client = userService.getUserByName(invoice.getClientName());
            findAndReplaceText(document, "<c_name>", client.getName());
            findAndReplaceText(document, "<c_reg_no>", client.getRegNo());
            findAndReplaceText(document, "<c_f_code>", client.getFCode());

            // Products data
            Set<InvoiceProduct> products = invoice.getInvoiceProducts();

            XWPFTable table = document.getTables().get(1);

            int productIndex = 0;
            for (InvoiceProduct p : products) {
                boolean rowFilled = false;

                for (XWPFTableRow row : table.getRows()) {
                    if (isRowEmpty(row)) {
                        fillRowWithData(row, p, productIndex);
                        rowFilled = true;
                        break;
                    }
                }

                if (!rowFilled) {
                    for (XWPFTableRow row : table.getRows()) {
                        if (!isRowEmpty(row)) {
                            fillRowWithData(row, p, productIndex);
                            break;
                        }
                    }
                }

                productIndex++;
            }

            try (OutputStream out = new FileOutputStream(outputPath)) {
                document.write(out);
            }

            return outputPath;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isRowEmpty(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            String cellText = cell.getText();
            if (cellText != null && !cellText.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void fillRowWithData(XWPFTableRow row, InvoiceProduct p, int productIndex) {
        setCellTextWithFont(row.getCell(0), String.valueOf(productIndex + 1));
        setCellTextWithFont(row.getCell(1), p.getName());
        setCellTextWithFont(row.getCell(2), p.getUnitOfMeasurement());
        setCellTextWithFont(row.getCell(3), String.valueOf(p.getQuantity()));
        setCellTextWithFont(row.getCell(4), String.valueOf(p.getUnitPrice()));
        setCellTextWithFont(row.getCell(5), String.valueOf(p.getTotalNoVat()));
        setCellTextWithFont(row.getCell(6), String.valueOf(p.getVat()));
    }

    private void setCellTextWithFont(XWPFTableCell cell, String text) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontFamily("Arial");
        run.setBold(true);
        run.setFontSize(9);
    }

    private void findAndReplaceText(XWPFDocument document, String placeholder, String replacement) {
        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        for (XWPFRun run : paragraph.getRuns()) {
                            String text = run.getText(0);
                            if (text != null && text.contains(placeholder)) {
                                text = text.replace(placeholder, replacement);
                                run.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }
}
