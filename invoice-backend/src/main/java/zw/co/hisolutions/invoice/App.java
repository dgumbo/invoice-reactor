package zw.co.hisolutions.invoice;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import java.io.*;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class App 
{
    public static void main( String[] args )
    {
        PDDocument pdfDocument = new PDDocument();
    	try {
	    	JSONObject jsonDocument = 
	    		(JSONObject)JSONValue
	    			.parse(new FileReader(new File("afew.json")));

	    	Invoice invoice = new Invoice(jsonDocument);

/*
            "first": "季",
            "last": "睿杰"
*/

            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);
            PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);

            invoice.printPDF(pdfDocument, contents);            

            pdfDocument.save("single-invoice.pdf");
    	} catch (Exception e) {
    		e.printStackTrace();
        } finally {
            try {
                pdfDocument.close();    
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
