package zw.co.hisolutions.invoice.invoices.service.impl;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.invoices.api.InvoiceController; 
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.common.entity.ShippingData; 
import zw.co.hisolutions.invoice.invoices.entity.Invoice;
import zw.co.hisolutions.invoice.invoices.entity.InvoiceRow;
import zw.co.hisolutions.invoice.invoices.entity.dao.InvoiceDao; 
import zw.co.hisolutions.invoice.invoices.service.InvoiceService;
import zw.co.hisolutions.invoice.pdfprint.service.PDFPrinterService;

/**
 *
 * @author dgumbo
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceDao invoiceDao;

    public InvoiceServiceImpl(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public int printAddress(PDPageContentStream contents, boolean rightSide, int startY, Address address) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        Color color = new Color(80, 80, 80);

        int x = rightSide ? 400 : 120;

        // int startY = 660;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, font, 10);
        headerPrinter.putText(x, startY, rightSide ? "Ship To:" : "Bill To:");

        startY -= 12;
        PDFPrinterService addressPrinter = new PDFPrinterService(contents, font, 10, color);
        addressPrinter.putText(x, startY, address.getFullName());
        startY -= 12;
        addressPrinter.putText(x, startY, address.getAddress1());
        startY -= 12;
        if (address.hasAddress2()) {
            addressPrinter.putText(x, startY, address.getAddress2());
            startY -= 12;
        }
        if (address.hasAddress2()) {
            addressPrinter.putText(x, startY, address.getAddress3());
            startY -= 12;
        }
        addressPrinter.putText(x, startY, address.getCity());
        startY -= 12;
        addressPrinter.putText(x, startY, address.getCountry());
        startY -= 12;

        return startY;
    }
  
   
    @Override
    public int printInvoiceRow(PDPageContentStream contents, int rowY, InvoiceRow invoiceRow) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 8);
        textPrinter.putText(60, rowY + 7, invoiceRow.getProduct().getNumber());
        textPrinter.putText(160, rowY + 7, invoiceRow.getProduct().getDescription());
        textPrinter.putTextToTheRight(420, rowY + 7, invoiceRow.getQuantityString());
        textPrinter.putTextToTheRight(490, rowY + 7, invoiceRow.getPriceString());
        textPrinter.putTextToTheRight(560, rowY + 7, invoiceRow.getTotalString());

        return rowY + 7;
    }

    @Override
    public int printHeader(PDDocument pdfDocument, PDPageContentStream contents, int headerStartY, Header header) throws IOException {
        int headerStartX = 120;
        PDFont fontAwesome = PDType0Font.load(pdfDocument, new File("./invoice-fonts/fontawesome-webfont.ttf"));

        PDImageXObject pdImage = PDImageXObject.createFromFile("./invoice-backend/logo.png", pdfDocument);
        final float width = 60f;
        final float scale = width / pdImage.getWidth();
        contents.drawImage(pdImage, 50, headerStartY - 44, width, pdImage.getHeight() * scale);

        PDFont headerFont = PDType1Font.HELVETICA_BOLD;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, headerFont, 16);
        headerPrinter.putText(headerStartX, headerStartY, "Heritage Innovative Solutions.");

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 10);
        textPrinter.putText(headerStartX, headerStartY - 20, "Stand Number 14657");
        textPrinter.putText(headerStartX, headerStartY - 32, "Galloway Park, Norton, Zimbabwe");
        textPrinter.putText(headerStartX + 10, headerStartY - 45, "  +263 773 632 856");
        textPrinter.putText(headerStartX + 10, headerStartY - 57, "  http://hisolutions.co.zw");

        PDFPrinterService fontAwesomePrinter = new PDFPrinterService(contents, fontAwesome, 10);
        fontAwesomePrinter.putText(headerStartX, headerStartY - 45, "\uf095");
        fontAwesomePrinter.putText(headerStartX, headerStartY - 57, "\uf0ac");

        Color color = new Color(200, 200, 200);
        PDFPrinterService invoiceHeaderPrinter = new PDFPrinterService(contents, font, 24, color);
        invoiceHeaderPrinter.putText(450, headerStartY, "INVOICE");

        textPrinter.putText(390, headerStartY - 20, "Invoice Date:");
        textPrinter.putText(390, headerStartY - 32, "Invoice Number:");
        textPrinter.putText(490, headerStartY - 20, header.getSDF().format(header.getDate()));
        textPrinter.putText(490, headerStartY - 32, header.getNumber());

        return headerStartY - 60;
    }

    private int maxRowSize = 23;
    private int maxPageWithSummation = 16;
    private int breakPoint = 12;

    @Override
    public void printPDF(PDDocument pdfDocument, PDPageContentStream contents, Invoice invoice) throws IOException {
        int nextStartY = 740;

        Header header = invoice.getHeader(); 
        nextStartY = printHeader(pdfDocument, contents, nextStartY, header);
        
        int numPrintedRows = 0;
        int rowsLeft = invoice.getRows().size();
        
        nextStartY -= 30;
        printHeaderRow(contents, nextStartY);
        printRowBackGround(contents, nextStartY - 21,
                rowsLeft < maxPageWithSummation ? maxPageWithSummation : maxRowSize
        );

        BigDecimal totalCost = BigDecimal.ZERO;
        for (InvoiceRow invoiceRow : invoice.getRows()) {
            numPrintedRows++;
            nextStartY -= 20;
            printInvoiceRow(contents, nextStartY, invoiceRow);
            totalCost = invoiceRow.addTotal(totalCost);
            if (newPageRequired(numPrintedRows, rowsLeft)) {
                rowsLeft -= numPrintedRows;
                numPrintedRows = 0;
                maxRowSize = 30;
                maxPageWithSummation = 23;
                breakPoint = 18;
                nextStartY = 660;
                contents = newPage(pdfDocument, contents, nextStartY,
                        rowsLeft < maxPageWithSummation ? maxPageWithSummation : maxRowSize,
                        invoice
                ); 
            }
        }

        /*
		First page with summation 16 rows
		First page without summation 24 rows
		Next page with summation 23 rows
		Next page without summation 31 rows

		16-24 = 12		
		24-31 = 18
         */
        nextStartY = 210;
        printSummary(contents, totalCost, nextStartY);
        printFooter(contents, nextStartY, invoice);
        contents.close();
    }

    private PDPageContentStream newPage(PDDocument pdfDocument, PDPageContentStream contents, int rowY, int numRows, Invoice invoice) throws IOException {
        contents.close();
        PDPage pdfPage = new PDPage();
        pdfDocument.addPage(pdfPage);
        contents = new PDPageContentStream(pdfDocument, pdfPage);
        Header header = invoice.getHeader();
        printHeader(pdfDocument, contents, 740, header);
        printHeaderRow(contents, rowY);
        printRowBackGround(contents, rowY - 21, numRows);
        return contents;
    }

    @Override
    public void printShippingData(PDPageContentStream contents, ShippingData shippingData) throws IOException {

        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, 570, 520, 20);
        contents.fillAndStroke();
        contents.addRect(50, 550, 520, 20);
        contents.stroke();

        final int headerY = 577;
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, font, 12);
        headerPrinter.putText(60, headerY, "Ship. number");
        headerPrinter.putText(160, headerY, "Sales Rep.");
        headerPrinter.putText(280, headerY, "Ship date");
        headerPrinter.putText(340, headerY, "Ship via");
        headerPrinter.putText(450, headerY, "Terms");
        headerPrinter.putText(510, headerY, "Due date");

        final int textY = 557;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 8);
        textPrinter.putText(60, textY, shippingData.getShipNumber());
        textPrinter.putText(160, textY, shippingData.getSalesRep());
        textPrinter.putText(280, textY, shippingData.getShipDateString());
        textPrinter.putText(340, textY, shippingData.getShipVia());
        textPrinter.putText(450, textY, shippingData.getTerms());
        textPrinter.putText(510, textY, shippingData.getDueDateString());
    }

    private boolean newPageRequired(int numPrintedRows, int rowsLeft) {
        if (numPrintedRows >= this.maxRowSize) {
            return true;
        }
        if (this.maxPageWithSummation < rowsLeft && rowsLeft < this.maxRowSize) {
            if (numPrintedRows >= this.breakPoint) {
                return true;
            }
        }
        return false;
    }

    public void printSummary(PDPageContentStream contents, BigDecimal totalCost, int summaryStartY) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);

        PDFPrinterService summeryLabelPrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinterService summeryValuePrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA, 12);

        BigDecimal subTotal = totalCost.multiply(new BigDecimal(0.8f));
        BigDecimal vatValue = totalCost.multiply(new BigDecimal(0.2f));
        subTotal = subTotal.setScale(2, RoundingMode.HALF_EVEN);
        vatValue = vatValue.setScale(2, RoundingMode.HALF_EVEN);
        totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);

        // int summaryStartY = 171;
        summeryLabelPrinter.putText(451, summaryStartY, "Sub Total");
        contents.addRect(450, summaryStartY - 17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summaryStartY - 15, "$ " + subTotal.toString());

        summeryLabelPrinter.putText(451, summaryStartY - 30, "Vat");
        contents.addRect(450, summaryStartY - 30 - 17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summaryStartY - 30 - 15, "$ " + vatValue.toString());

        summeryLabelPrinter.putText(451, summaryStartY - 60, "Total Price");
        contents.addRect(450, summaryStartY - 60 - 17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summaryStartY - 60 - 15, "$ " + totalCost.toString());
    }

    public void printRowBackGround(PDPageContentStream contents, int rowY, int numRows) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);

        boolean odd = true;
        for (int i = 0; i < numRows; i++) {
            if (odd) {
                contents.addRect(51, rowY, 518, 20);
                contents.fill();
            }

            contents.moveTo(50, rowY);
            contents.lineTo(50, rowY + 20);
            contents.moveTo(570, rowY);
            contents.lineTo(570, rowY + 20);
            contents.stroke();
            rowY -= 20;
            odd = !odd;
        }

        contents.moveTo(50, rowY + 20);
        contents.lineTo(570, rowY + 20);
        contents.stroke();
    }

    public void printHeaderRow(PDPageContentStream contents, int headerY) throws IOException {
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, headerY, 520, 20);
        contents.fillAndStroke();

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, font, 12);
        headerPrinter.putText(60, headerY + 7, "Product #");
        headerPrinter.putText(160, headerY + 7, "Description");
        headerPrinter.putText(380, headerY + 7, "Quantity");
        headerPrinter.putText(440, headerY + 7, "Unit Price");
        headerPrinter.putText(510, headerY + 7, "Total Price");
    }

    public void printFooter(PDPageContentStream contents, Integer startY, Invoice invoice) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.addRect(50, 35, 370, 135);
        contents.stroke();

        PDFPrinterService footerLabelPrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinterService footerValuePrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA, 8);
        footerLabelPrinter.putText(50, startY, "Notes");

        int rowY = startY - 20;
        StringBuilder sb = new StringBuilder();
        for (String s : invoice.getNotes().split(" ")) {
            if (footerValuePrinter.widthOfText(sb.toString() + " " + s) > 365) {
                if (rowY < 50) {
                    sb.append("...");
                    footerValuePrinter.putText(55, rowY, sb.toString());
                    sb = new StringBuilder();
                    break;
                }
                footerValuePrinter.putText(55, rowY, sb.toString());
                rowY -= 10;
                sb = new StringBuilder();
            }
            sb.append(s);
            sb.append(" ");
        }
        footerValuePrinter.putText(55, rowY, sb.toString());
    }

    public List<InvoiceRow> getRows(Invoice invoice) {
        return invoice.getRows();
    }
    
    @Override
    public JpaRepository<Invoice, Long> getDao() {
        return invoiceDao;
    }

    @Override
    public Class getController() {
        return InvoiceController.class;
    }

}
