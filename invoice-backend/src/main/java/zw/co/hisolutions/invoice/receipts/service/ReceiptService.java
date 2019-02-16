package zw.co.hisolutions.invoice.receipts.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.core.io.Resource;
import zw.co.hisolutions.common.service.BasicService;
import zw.co.hisolutions.invoice.receipts.entity.Receipt;

/**
 *
 * @author dgumbo
 */
public interface ReceiptService extends BasicService<Receipt, Long> {

    PDDocument printPDF( Receipt receipt) throws IOException; 

    String getMimeType(Resource file) throws IOException;

    String getMimeType(ByteArrayInputStream in) throws IOException;
}
