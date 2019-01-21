package zw.co.hisolutions.invoice.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.entity.InvoiceRow;
import zw.co.hisolutions.invoice.service.InvoiceRowService;


@RestController
@RequestMapping("/api/invoice-row")
public class InvoiceRowController extends BasicRestController<InvoiceRow, Long>{   
    InvoiceRowService invoiceRowService;

    public InvoiceRowController(InvoiceRowService invoiceRowService) {
        this.invoiceRowService = invoiceRowService;
    } 
    
    @Override
    public BasicService getService() {
        return invoiceRowService;
    }
 
    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
}
