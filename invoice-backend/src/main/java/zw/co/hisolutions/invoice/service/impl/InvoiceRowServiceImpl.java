package zw.co.hisolutions.invoice.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.api.InvoiceRowController;
import zw.co.hisolutions.invoice.entity.InvoiceRow;
import zw.co.hisolutions.invoice.entity.dao.InvoiceRowDao;
import zw.co.hisolutions.invoice.service.InvoiceRowService;

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
