package zw.co.hisolutions.invoice.document;
 
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.awt.Color;
import lombok.Data;
import zw.co.hisolutions.invoice.PDFPrinter;

@Data
public class Address { 
    private String title;
    private String first;
    private String last;
    private String address1;
    private String address2;
    private String address3;
    private String city;
//    private String state;
//    private String zipCode;
    private String country;

    public int printPDF(PDPageContentStream contents, boolean rightSide, int startY) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        Color color = new Color(80, 80, 80);

        int x = rightSide ? 400 : 120;

        // int startY = 660;

        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 10);
        headerPrinter.putText(x, startY, rightSide ?  "Ship To:" :"Bill To:");

        startY -= 12;
        PDFPrinter addressPrinter = new PDFPrinter(contents, font, 10, color);
        addressPrinter.putText(x, startY, getFullName());
        startY -= 12;
        addressPrinter.putText(x, startY, getAddress1());
        startY -= 12;
        if (hasAddress2()) {
            addressPrinter.putText(x, startY, getAddress2());
            startY -= 12;
        } 
        if (hasAddress2()) {
            addressPrinter.putText(x, startY, getAddress3());
            startY -= 12;
        }
        addressPrinter.putText(x, startY, getCity());
        startY -= 12;
        addressPrinter.putText(x, startY,  getCountry());
        startY -= 12;
                
        return startY;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getTitle()).append(" ");
        sb.append(this.getFirst()).append(" ");
        sb.append(this.getLast());
        return sb.toString();
    } 

    public boolean hasAddress2() {
        return this.address2 != null && !this.address2.trim().isEmpty();
    } 

    public boolean hasAddress3() {
        return this.address3 != null && !this.address3.trim().isEmpty();
    }  
}
