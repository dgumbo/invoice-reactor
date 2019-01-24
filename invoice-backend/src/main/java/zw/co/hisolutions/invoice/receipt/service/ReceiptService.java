package zw.co.hisolutions.invoice.receipt.service;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.common.entity.Address;  
import zw.co.hisolutions.invoice.common.entity.Header; 
import zw.co.hisolutions.invoice.common.entity.ShippingData;
import zw.co.hisolutions.invoice.receipt.entity.Receipt;
import zw.co.hisolutions.invoice.receipt.entity.ReceiptRow; 

/**
 *
 * @author dgumbo
 */
public interface ReceiptService   extends BasicService<Receipt, Long>{   
    int printAddress(PDPageContentStream contents, boolean rightSide, int startY, Address address) throws IOException;
    
    int printReceiptRow(PDPageContentStream contents, int rowY, ReceiptRow receiptRow) throws IOException ;
    
    int printHeader(PDDocument pdfDocument, PDPageContentStream contents, int headerStartY, Header header) throws IOException;
    
    void printPDF(PDDocument pdfDocument, PDPageContentStream contents, Receipt receipt) throws IOException;
    
    void printShippingData(PDPageContentStream contents, ShippingData shippingData) throws IOException;
}
