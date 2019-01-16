package zw.co.hisolutions.invoice.document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.json.simple.JSONObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import zw.co.hisolutions.invoice.PDFPrinter;
import java.awt.Color;
import java.io.IOException;
import lombok.Data;

@Data
public class InvoiceRow {

    private String productNumber;
    private String productDescription;
    private double quantity;
    private BigDecimal price;

    public InvoiceRow(JSONObject jsonInvoiceRow) {
        if (jsonInvoiceRow.containsKey("productId")) {
            this.setProductNumber((String) jsonInvoiceRow.get("productId"));
        }
        if (jsonInvoiceRow.containsKey("description")) {
            this.setProductDescription((String) jsonInvoiceRow.get("description"));
        }
        if (jsonInvoiceRow.containsKey("quantity")) {
            this.setQuantity((String) jsonInvoiceRow.get("quantity"));
        }
        if (jsonInvoiceRow.containsKey("unitPrice")) {
            this.setPrice((String) jsonInvoiceRow.get("unitPrice"));
        }
    }

    public BigDecimal addTotal(BigDecimal totalCost) {
        return totalCost.add(this.getTotal());
    }

    public void printPDF(PDPageContentStream contents, int rowY) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 8);
        textPrinter.putText(60, rowY + 7, this.getProductNumber());
        textPrinter.putText(160, rowY + 7, this.getProductDescription());
        textPrinter.putTextToTheRight(420, rowY + 7, this.getQuantityString());
        textPrinter.putTextToTheRight(490, rowY + 7, this.getPriceString());
        textPrinter.putTextToTheRight(560, rowY + 7, this.getTotalString());
    }

    public String getTotalString() {
        BigDecimal printTotal = getTotal();
        printTotal.setScale(2, RoundingMode.HALF_EVEN);
        return printTotal.toString();
    }

    public String getPriceString() {
        BigDecimal printPrice = getPrice();
        printPrice.setScale(2, RoundingMode.HALF_EVEN);
        return printPrice.toString();
    }

    public String getQuantityString() {
        return Double.toString(quantity);
    }

    public BigDecimal getTotal() {
        return this.price.multiply(new BigDecimal(quantity));
    }

    public void setQuantity(String quantity) {
        this.quantity = Double.parseDouble(quantity.replace(",", "."));
    }

    public void setPrice(double price) {
        this.price = new BigDecimal(price);
    }

    public void setPrice(String price) {
        this.price = new BigDecimal(price.replace(",", "."));
    }
}
