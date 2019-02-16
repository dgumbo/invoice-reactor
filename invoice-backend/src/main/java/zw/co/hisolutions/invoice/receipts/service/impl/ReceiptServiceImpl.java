package zw.co.hisolutions.invoice.receipts.service.impl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.receipts.entity.Receipt;
import zw.co.hisolutions.invoice.receipts.entity.ReceiptLine;
import zw.co.hisolutions.invoice.receipts.service.ReceiptService;
import zw.co.hisolutions.invoice.pdfprint.service.PDFPrinterService;
import zw.co.hisolutions.invoice.receipts.api.ReceiptController;
import zw.co.hisolutions.invoice.receipts.entity.dao.ReceiptDao;

/**
 *
 * @author dgumbo
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {

   private final ReceiptDao receiptDao;

    private final Tika tika = new Tika();

    private int maxRowSize = 23;
    private int maxPageWithSummation = 16;
    private int breakPoint = 12;

    @Autowired
    private CreateTempReceiptService tempReceiptService;

    public ReceiptServiceImpl(ReceiptDao receiptDao) {
        this.receiptDao = receiptDao;
    }

    @Override
    public Receipt create(Receipt data) throws IllegalArgumentException {
        return tempReceiptService.create(this, data);
    }

    final int documentEndX = 550;

    @Override
    public PDDocument printPDF(Receipt receipt) throws IOException {
        PDDocument pdfDocument = new PDDocument();

        PDPage pdfPage = new PDPage();
        pdfDocument.addPage(pdfPage);
        PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);

        int nextStartY = 740;

        Header header = receipt.getHeader();
        printHeader(contents, header, documentEndX);

        nextStartY = printReceiptCaption(contents, nextStartY, documentEndX);

        nextStartY = printLetterHead(pdfDocument, contents, nextStartY);

        Address billingAddress = receipt.getBillTo();
        nextStartY = printBillingData(contents, nextStartY, billingAddress);

        nextStartY = printReceiptMetadata(contents, nextStartY, header);

        int numPrintedRows = 0;
        int rowsLeft = receipt.getReceiptLines().size();

        nextStartY = printServicesListHeaderRow(contents, nextStartY, documentEndX);

        BigDecimal totalCost = BigDecimal.ZERO;
        for (ReceiptLine receiptRow : receipt.getReceiptLines()) {
            numPrintedRows++;

            boolean odd = numPrintedRows % 2 == 0;

            nextStartY = printReceiptRow(contents, nextStartY, receiptRow, odd, documentEndX);
            totalCost = receiptRow.addTotal(totalCost);
            if (newPageRequired(numPrintedRows, rowsLeft)) {
                rowsLeft -= numPrintedRows;
                numPrintedRows = 0;
                maxRowSize = 30;
                maxPageWithSummation = 23;
                breakPoint = 18;
                nextStartY = 660;
                contents = newPage(pdfDocument, contents, nextStartY,
                        rowsLeft < maxPageWithSummation ? maxPageWithSummation : maxRowSize,
                        receipt
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
        boolean includeVat = false;
        nextStartY = printServicesListSummary(contents, totalCost, nextStartY, includeVat, documentEndX);
        printEndNotes(contents, nextStartY, receipt, documentEndX);
        printFooter(contents);
        contents.close();

        return pdfDocument;
    }

    private void printHeader(PDPageContentStream contents, Header header, int docEndX) throws IOException {
        int headerStartY = 760;

        PDFont font = PDType1Font.HELVETICA;
        Color color = new Color(100, 100, 100);
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 12, color);

        textPrinter.putTextToTheRight(docEndX, headerStartY, header.getNumber());
    }

    private int printReceiptCaption(PDPageContentStream contents, int headerStartY, int docEndX) throws IOException {
        PDFont receiptCaptionFont = PDType1Font.HELVETICA_BOLD;

        Color color = new Color(50, 50, 50);

        PDFPrinterService receiptHeaderPrinter = new PDFPrinterService(contents, receiptCaptionFont, 18, color);
        receiptHeaderPrinter.putTextToTheRight(docEndX, headerStartY - 14, "Receipt");
        headerStartY -= 15;

        return headerStartY;
    }

    private int printLetterHead(PDDocument pdfDocument, PDPageContentStream contents, int headerStartY) throws IOException {
        int headerStartX = 165;
        final int letterHeadRowHeight = 15;

        PDFont fontAwesome = PDType0Font.load(pdfDocument, new File("./fontawesome-webfont.ttf"));

        PDImageXObject pdImage = PDImageXObject.createFromFile("./logo.png", pdfDocument);
        final float width = 90f;
        final float scale = width / pdImage.getWidth();
        contents.drawImage(pdImage, 50, headerStartY - pdImage.getHeight() * scale + 20, width, pdImage.getHeight() * scale);

        PDFont headerFont = PDType1Font.HELVETICA_BOLD;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, headerFont, 16);
        headerPrinter.putText(headerStartX, headerStartY, "Heritage Innovative Solutions");
        headerStartY -= letterHeadRowHeight;

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 10);
        PDFPrinterService fontAwesomePrinter = new PDFPrinterService(contents, fontAwesome, 10);

        textPrinter.putText(headerStartX, headerStartY, "Stand Number 14657");
        headerStartY -= letterHeadRowHeight;
        textPrinter.putText(headerStartX, headerStartY, "Galloway Park, Norton, Zimbabwe");
        headerStartY -= letterHeadRowHeight;
        fontAwesomePrinter.putText(headerStartX, headerStartY, "\uf095");
        textPrinter.putText(headerStartX + 10, headerStartY, "  +263 773 632 856");
        headerStartY -= letterHeadRowHeight;
        fontAwesomePrinter.putText(headerStartX, headerStartY, "\uf0ac");
        textPrinter.putText(headerStartX + 10, headerStartY, "  http://hisolutions.co.zw");

        return headerStartY;
    }

    private int printBillingData(PDPageContentStream contents, int startY, Address address) throws IOException {
        startY -= 45;
        PDFont font = PDType1Font.HELVETICA;

        int startX = 50;

        PDFont headerFont = PDType1Font.HELVETICA;
        Color color = new Color(50, 50, 50);
        PDFPrinterService billToHeaderPrinter = new PDFPrinterService(contents, headerFont, 14, color);
        billToHeaderPrinter.putText(startX, startY, "Bill To");

        PDFPrinterService addressPrinter = new PDFPrinterService(contents, font, 11);

        if (address.hasFullName()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getFullName());
        }
        if (address.hasAddress1()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getAddress1());
        }
        if (address.hasAddress2()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getAddress2());
        }
        if (address.hasAddress3()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getAddress3());
        }

        if (address.hasCity()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getCity());
        }

        if (address.hasCountry()) {
            startY -= 14;
            addressPrinter.putText(startX, startY, address.getCountry());
        }

        return startY;
    }

    private int printReceiptMetadata(PDPageContentStream contents, int headerStartY, Header header) throws IOException {
        int headerStartX = 50;
        headerStartY -= 40;

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 10);

        textPrinter.putText(headerStartX, headerStartY, "Number : ");
        textPrinter.putText(headerStartX + 65, headerStartY, header.getNumber());
        headerStartY -= 15;

        textPrinter.putText(headerStartX, headerStartY, "Date : ");
        textPrinter.putText(headerStartX + 65, headerStartY, header.getSDF().format(header.getDate()));
        headerStartY -= 15;

        textPrinter.putText(headerStartX, headerStartY, "Invoice Ref : ");
        textPrinter.putText(headerStartX + 65, headerStartY, header.getInvoiceRef());

        return headerStartY;
    }

    private int printServicesListHeaderRow(PDPageContentStream contents, int nextStartY, int docEndX) throws IOException {
        int headerRowHeight = 35;
        int topMarginSize = 40;
        nextStartY = nextStartY - topMarginSize - headerRowHeight * 4 / 6;

        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);

        int firstRectEndX = 430;
        int secondRectEndX = docEndX - firstRectEndX;
        contents.setStrokingColor(strokeColor);

        contents.setNonStrokingColor(fillColor);

        contents.addRect(50, nextStartY, firstRectEndX - 50, headerRowHeight);
        contents.addRect(firstRectEndX, nextStartY, secondRectEndX, headerRowHeight);
        contents.fillAndStroke();

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService headerPrinter = new PDFPrinterService(contents, font, 12);
        headerPrinter.putText(60, nextStartY + headerRowHeight * 2 / 6, "Description");
        headerPrinter.putText(440, nextStartY + headerRowHeight * 2 / 6, "Amount");

        return nextStartY;
    }

    private int printReceiptRow(PDPageContentStream contents, int startY, ReceiptLine receiptRow, boolean odd, int docEndX) throws IOException {
        int topMargin = 5;
        int rowHeight = 15;

        int descriptionTdWidth = 365;

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinterService textPrinter = new PDFPrinterService(contents, font, 10);

        List<String> lines = new ArrayList();

        List<String> breaks = Arrays.asList(receiptRow.getProduct().getDescription().split("\\n"));
        for (String br : breaks) {
            StringBuilder sb = new StringBuilder();
            for (String s : br.split(" ")) {
                if (textPrinter.widthOfText(sb.toString() + s + " ") > descriptionTdWidth) {
                    lines.add(sb.toString());
                    sb = new StringBuilder();
                    sb.append("  ");
                }
                sb.append(s).append(" ");
            }

            if (sb.length() >= 1) {
                lines.add(sb.toString());
            }
        }

        printRowBackGround(contents, startY, rowHeight
                * lines.size() + topMargin, odd, documentEndX);

        startY -= topMargin;
        int yPos = startY - rowHeight + rowHeight * 2 / 5;
        for (String line : lines) {
            textPrinter.putText(60, yPos, line);
            yPos -= rowHeight;
        }

        startY -= rowHeight * lines.size();

        textPrinter.putTextToTheRight(
                docEndX - 10, startY + rowHeight * lines.size() * 2 / 5, receiptRow.getTotalString());

        return startY;
    }

    private void printRowBackGround(PDPageContentStream contents, int rowY, int height, boolean odd, int docEndX) throws IOException {

        int firstRectEndX = 430;
        int secondRectEndX = docEndX - firstRectEndX;

        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);

        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);

        contents.addRect(50, rowY - height, firstRectEndX - 50, height);
        contents.addRect(firstRectEndX, rowY - height, secondRectEndX, height);

        if (odd) {
            contents.fillAndStroke();
        } else {
            contents.stroke();
        }
    }

    private int printServicesListSummary(PDPageContentStream contents, BigDecimal totalCost, int summaryStartY, boolean includeVat, int docEndX) throws IOException {
        int summaryRowHeight = 35;
        summaryStartY -= summaryRowHeight;

        int firstRectEndX = 430;
        int secondRectEndX = docEndX - firstRectEndX;

        BigDecimal subTotal = totalCost.multiply(new BigDecimal(0.8f));
        BigDecimal vatValue = totalCost.multiply(new BigDecimal(0.2f));
        subTotal = subTotal.setScale(2, RoundingMode.HALF_EVEN);
        vatValue = vatValue.setScale(2, RoundingMode.HALF_EVEN);
        totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);

        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);

        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);

        PDFPrinterService summaryPrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA_BOLD, 12);

        int summaryLabelRightX = docEndX - 150;

        if (includeVat) {
            // /* Print Subtotal */
            contents.addRect(firstRectEndX, summaryStartY, secondRectEndX, summaryRowHeight);
            contents.stroke();

            summaryPrinter.putTextToTheRight(summaryLabelRightX, summaryStartY + summaryRowHeight * 1 / 4, "Sub Total : ");
            summaryPrinter.putTextToTheRight(docEndX - 10, summaryStartY + summaryRowHeight * 1 / 4, "$ " + subTotal.toString());

            summaryStartY -= summaryRowHeight;
            // /* End Print Subtotal */

            // /* Print VAT */
            contents.addRect(firstRectEndX, summaryStartY, secondRectEndX, summaryRowHeight);
            contents.stroke();

            summaryPrinter.putTextToTheRight(summaryLabelRightX, summaryStartY + summaryRowHeight * 1 / 4, "Vat : ");
            summaryPrinter.putTextToTheRight(docEndX - 10, summaryStartY + summaryRowHeight * 1 / 4, "$ " + vatValue.toString());

            summaryStartY -= summaryRowHeight;
            // /* End Print VAT */
        }

        // /* Print Total */
        contents.addRect(firstRectEndX, summaryStartY, secondRectEndX, summaryRowHeight);
        contents.stroke();

        summaryPrinter.putTextToTheRight(summaryLabelRightX, summaryStartY + summaryRowHeight * 1 / 4, "Total : ");
        summaryPrinter.putTextToTheRight(docEndX - 10, summaryStartY + summaryRowHeight * 1 / 4, "$ " + totalCost.toString());
        // /* End Print Total */

        return summaryStartY;
    }

    private int printEndNotes(PDPageContentStream contents, Integer startY, Receipt receipt, int docEndX) throws IOException {
        int topMargin = 30;
        startY -= topMargin;

        PDFPrinterService footerValuePrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA, 9);

        int rowHeight = 14;
        int footerRowWidth = 800;

        List<String> footerLines = new ArrayList();

        List<String> breaks = Arrays.asList(receipt.getEndNotes().split("\\n"));
        for (String br : breaks) {
            StringBuilder sb = new StringBuilder();
            for (String s : br.split(" ")) {
                if (footerValuePrinter.widthOfText(sb.toString() + s + " ") > footerRowWidth) {
                    footerLines.add(sb.toString());
                    sb = new StringBuilder();
                    sb.append("  ");
                }
                sb.append(s).append(" ");
            }

            if (sb.length() >= 1) {
                footerLines.add(sb.toString());
            }
        }

        int yPos = startY - rowHeight + rowHeight * 2 / 5;
        for (String line : footerLines) {
            int leftMargin = 50;
            int textWidth = footerValuePrinter.widthOfText(line);
            int startXPos = footerRowWidth - textWidth >= 0 ? ((footerRowWidth - textWidth) / 5 * 2) - (100 * textWidth / footerRowWidth) : leftMargin;

            footerValuePrinter.putText(startXPos, yPos, line);
            yPos -= rowHeight;
        }

        return startY;
    }

    private int printFooter(PDPageContentStream contents) throws IOException {
        int bottomMargin = 30;
        int leftMargin = 50;

        PDFPrinterService footerValuePrinter = new PDFPrinterService(contents, PDType1Font.HELVETICA_OBLIQUE, 8);

        String line = "* This Document Serves as a Confirmation of Receipt of Payment for Above Listed Services/Products.";

        footerValuePrinter.putText(leftMargin, bottomMargin, line);

        return bottomMargin;
    }

    private PDPageContentStream newPage(PDDocument pdfDocument, PDPageContentStream contents, int rowY, int numRows, Receipt receipt) throws IOException {
        contents.close();
        PDPage pdfPage = new PDPage();
        pdfDocument.addPage(pdfPage);
        contents = new PDPageContentStream(pdfDocument, pdfPage);
        Header header = receipt.getHeader();
        printLetterHead(pdfDocument, contents, 740);
        printReceiptMetadata(contents, 740, header);
        int nextStartY = printServicesListHeaderRow(contents, rowY, documentEndX);
        // printRowBackGround(contents, rowY - 21, numRows);
        return contents;
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

    public List<ReceiptLine> getRows(Receipt receipt) {
        return receipt.getReceiptLines();
    }

    @Override
    public JpaRepository<Receipt, Long> getDao() {
        return receiptDao;
    }

    @Override
    public Class
            getController() {
        return ReceiptController.class;
    }

    @Override
    public String getMimeType(Resource file) throws IOException {
        return tika.detect(file.getInputStream());
    }

    @Override
    public String getMimeType(ByteArrayInputStream in) throws IOException {
        return tika.detect(in);
    }

}
