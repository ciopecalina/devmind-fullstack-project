package org.ciopecalina.invoicingapp.services;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class WordDocumentService {

    private static final String INVOICE_DIRECTORY = "src/main/resources/invoices/";
    private static final String TEMPLATE_PATH = "src/main/resources/templates/invoice-template.docx";

    public String createDocument(String invoiceSeries, String invoiceNo) {
        String wordPath = createWordDocument(invoiceSeries, invoiceNo);
//        if (wordPath == null) return null;
//
//        String pdfPath = convertWordDocumentToPDF(invoiceSeries, invoiceNo);
//        return pdfPath;

        return wordPath;
    }

    public String createWordDocument(String invoiceSeries, String invoiceNo) {
        String outputPath = INVOICE_DIRECTORY + "Invoice_" + invoiceSeries + "-" + invoiceNo + ".docx";

        try (InputStream resourceStream = new FileInputStream(TEMPLATE_PATH)) {
            XWPFDocument document = new XWPFDocument(resourceStream);

            // Invoice data
            findAndReplaceText(document, "<s>", invoiceSeries);
            findAndReplaceText(document, "<no>", invoiceNo);
            findAndReplaceText(document, "<d>", "10/12/2024");

            // User data
            findAndReplaceText(document, "<u_name>", "34567");
            findAndReplaceText(document, "<u_reg_no>", "2345678");
            findAndReplaceText(document, "<u_f_code>", "aaa");
            findAndReplaceText(document, "<u_address>", "aaa");
            findAndReplaceText(document, "<u_iban>", "aaa");
            findAndReplaceText(document, "<u_bank>", "aaa");

            // Client data
            findAndReplaceText(document, "<c_name>", "aaa");
            findAndReplaceText(document, "<c_reg_no>", "aaa");
            findAndReplaceText(document, "<c_f_code>", "aaa");
            findAndReplaceText(document, "<c_address>", "aaa");

            //Products
            findAndReplaceText(document, "<p_no>", "test");
            findAndReplaceText(document, "<p_name>", "test");
            findAndReplaceText(document, "<uom>", "test");
            findAndReplaceText(document, "<qty>", "test");
            findAndReplaceText(document, "<p_tv>", "test");
            findAndReplaceText(document, "<p_tnv>", "test");
            findAndReplaceText(document, "<p_v>", "test");

            //Invoice data
            findAndReplaceText(document, "<tnv>", "test");
            findAndReplaceText(document, "<tv>", "test");
            findAndReplaceText(document, "<t>", "test");

            // Save as Word file
            try (OutputStream out = new FileOutputStream(outputPath)) {
                document.write(out);
            }

            return outputPath;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public String convertWordDocumentToPDF(String invoiceSeries, String invoiceNo) {
//        String wordPath = INVOICE_DIRECTORY + "Invoice_" + invoiceSeries + "-" + invoiceNo + ".docx";
//        String pdfPath = wordPath.replace(".docx", ".pdf");
//
//        try {
//            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
//            document.open();
//
//            XWPFDocument doc = new XWPFDocument(Files.newInputStream(Paths.get(wordPath)));
//            for (XWPFParagraph p : doc.getParagraphs()) {
//                document.add(new Paragraph(p.getText()));
//            }
//            document.close();
//
//            return pdfPath;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

//    private void findAndReplace(XWPFDocument document, String placeholder, String replacement) {
//        for (XWPFParagraph paragraph : document.getParagraphs()) {
//            for (XWPFRun run : paragraph.getRuns()) {
//                String text = run.getText(0);
//                if (text != null && text.contains(placeholder)) {
//                    text = text.replace(placeholder, replacement);
//                    run.setText(text, 0);
//                }
//            }
//        }
//    }

}
