package zw.co.hisolutions.invoice.document;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import zw.co.hisolutions.invoice.PDFPrinter;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import lombok.Data;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

@Data
public class Header {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    private Date invoiceDate;
    private String invoiceNumber;
    //private final int headerStartY = 740;
    private final int headerStartX = 120;

    public String getInvoiceDateString() {
        return sdf.format(this.invoiceDate);
    }

    public int printPDF(PDDocument pdfDocument, PDPageContentStream contents, int headerStartY) throws IOException {

        PDFont fontAwesome = PDType0Font.load(pdfDocument, new File("fontawesome-webfont.ttf"));

        PDImageXObject pdImage = PDImageXObject.createFromFile("logo.png", pdfDocument);
        final float width = 60f;
        final float scale = width / pdImage.getWidth();
        contents.drawImage(pdImage, 50, headerStartY - 44, width, pdImage.getHeight() * scale);

        PDFont headerFont = PDType1Font.HELVETICA_BOLD;
        PDFPrinter headerPrinter = new PDFPrinter(contents, headerFont, 16);
        headerPrinter.putText(headerStartX, headerStartY, "Heritage Innovative Solutions.");

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 10);
        textPrinter.putText(headerStartX, headerStartY - 20, "Stand Number 14657");
        textPrinter.putText(headerStartX, headerStartY - 32, "Galloway Park, Norton, Zimbabwe");
        textPrinter.putText(headerStartX + 10, headerStartY - 45, "  +263 773 632 856");
        textPrinter.putText(headerStartX + 10, headerStartY - 57, "  www.hisolutions.co.zw");

        PDFPrinter fontAwesomePrinter = new PDFPrinter(contents, fontAwesome, 10);
        fontAwesomePrinter.putText(headerStartX, headerStartY - 45, "\uf095");
        fontAwesomePrinter.putText(headerStartX, headerStartY - 57, "\uf0ac");

        Color color = new Color(200, 200, 200);
        PDFPrinter invoiceHeaderPrinter = new PDFPrinter(contents, font, 24, color);
        invoiceHeaderPrinter.putText(450, headerStartY, "INVOICE");

        textPrinter.putText(390, headerStartY - 20, "Invoice Date:");
        textPrinter.putText(390, headerStartY - 32, "Invoice Number:");
        textPrinter.putText(490, headerStartY - 20, this.getInvoiceDateString());
        textPrinter.putText(490, headerStartY - 32, this.getInvoiceNumber());

        return headerStartY - 60;
    }
}
