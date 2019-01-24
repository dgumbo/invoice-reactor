package zw.co.hisolutions.invoice.receipt.service.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.receipt.entity.Receipt;
import zw.co.hisolutions.invoice.receipt.service.ReceiptService;

/**
 *
 * @author dgumbo
 */

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController extends BasicRestController<Receipt, Long>{

    ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    } 
    
    @Override
    public BasicService getService() {
        return receiptService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
   
}
