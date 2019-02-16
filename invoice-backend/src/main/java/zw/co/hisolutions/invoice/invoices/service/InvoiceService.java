package zw.co.hisolutions.invoice.invoices.service;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.common.entity.ShippingData; 
import zw.co.hisolutions.invoice.invoices.entity.Invoice;
import zw.co.hisolutions.invoice.invoices.entity.InvoiceRow; 

/**
 *
 * @author dgumbo
 */
public interface InvoiceService  extends BasicService<Invoice, Long>{  

    int printAddress(PDPageContentStream contents, boolean rightSide, int startY, Address address) throws IOException;
    
    int printInvoiceRow(PDPageContentStream contents, int rowY, InvoiceRow invoiceRow) throws IOException ;
    
    int printHeader(PDDocument pdfDocument, PDPageContentStream contents, int headerStartY, Header header) throws IOException;
    
    void printPDF(PDDocument pdfDocument, PDPageContentStream contents, Invoice invoice) throws IOException;
    
    void printShippingData(PDPageContentStream contents, ShippingData shippingData) throws IOException;
}
