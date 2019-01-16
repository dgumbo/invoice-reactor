package zw.co.hisolutions.invoice.document;

import java.util.Date;
import java.text.SimpleDateFormat;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Color;
import zw.co.hisolutions.invoice.PDFPrinter;
import java.io.IOException;
import lombok.Data;

@Data
public class ShippingData1 {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String shipNumber;
    private String salesRep;
    private Date shipDate;
    private String shipVia;
    private String terms;
    private Date dueDate;

//    public ShippingData(JSONObject jsonShippingData) {
//        if (jsonShippingData.containsKey("shipNumber")) {
//            this.setShipNumber((String) jsonShippingData.get("shipNumber"));
//        }
//        if (jsonShippingData.containsKey("salesRep")) {
//            this.setSalesRep((String) jsonShippingData.get("salesRep"));
//        }
//        if (jsonShippingData.containsKey("shipDate")) {
//            this.setShipDate((String) jsonShippingData.get("shipDate"));
//        }
//        if (jsonShippingData.containsKey("shipVia")) {
//            this.setShipVia((String) jsonShippingData.get("shipVia"));
//        }
//        if (jsonShippingData.containsKey("terms")) {
//            this.setTerms((String) jsonShippingData.get("terms"));
//        }
//        if (jsonShippingData.containsKey("dueDate")) {
//            this.setDueDate((String) jsonShippingData.get("dueDate"));
//        }
//    }

    public void printPDF(PDPageContentStream contents) throws IOException {

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
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 12);
        headerPrinter.putText(60, headerY, "Ship. number");
        headerPrinter.putText(160, headerY, "Sales Rep.");
        headerPrinter.putText(280, headerY, "Ship date");
        headerPrinter.putText(340, headerY, "Ship via");
        headerPrinter.putText(450, headerY, "Terms");
        headerPrinter.putText(510, headerY, "Due date");

        final int textY = 557;
        PDFPrinter textPrinter = new PDFPrinter(contents, font, 8);
        textPrinter.putText(60, textY, this.getShipNumber());
        textPrinter.putText(160, textY, this.getSalesRep());
        textPrinter.putText(280, textY, this.getShipDateString());
        textPrinter.putText(340, textY, this.getShipVia());
        textPrinter.putText(450, textY, this.getTerms());
        textPrinter.putText(510, textY, this.getDueDateString());
    } 

    public String getShipDateString() {
        return sdf.format(this.shipDate);
    } 

    public String getDueDateString() {
        return sdf.format(this.dueDate);
    }
}
