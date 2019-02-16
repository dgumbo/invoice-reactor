package zw.co.hisolutions.invoice.invoices.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import zw.co.hisolutions.invoice.invoices.entity.InvoiceRow;
 
public interface InvoiceRowDao extends JpaRepository<InvoiceRow, Long>{ 
    
}
