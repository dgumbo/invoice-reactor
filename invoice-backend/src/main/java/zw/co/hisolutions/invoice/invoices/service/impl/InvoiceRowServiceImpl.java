package zw.co.hisolutions.invoice.invoices.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.invoices.api.InvoiceRowController;
import zw.co.hisolutions.invoice.invoices.entity.InvoiceRow;
import zw.co.hisolutions.invoice.invoices.entity.dao.InvoiceRowDao;
import zw.co.hisolutions.invoice.invoices.service.InvoiceRowService;

@Service
public class InvoiceRowServiceImpl implements InvoiceRowService{ 

    private final InvoiceRowDao invoiceRowDao;

    public InvoiceRowServiceImpl(InvoiceRowDao invoiceRowDao) {
        this.invoiceRowDao = invoiceRowDao;
    }
    
    @Override
    public JpaRepository<InvoiceRow, Long> getDao() {
        return invoiceRowDao;
    }

    @Override
    public Class getController() {
        return InvoiceRowController.class;
    }
}
