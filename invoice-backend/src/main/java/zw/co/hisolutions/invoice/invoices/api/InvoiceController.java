package zw.co.hisolutions.invoice.invoices.api;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.invoices.entity.Invoice;
import zw.co.hisolutions.invoice.invoices.service.InvoiceService;


@RestController
@RequestMapping("/api/invoice")
public class InvoiceController extends BasicRestController<Invoice, Long>{

    InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    } 
    
    @Override
    public BasicService getService() {
        return invoiceService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
   
}
