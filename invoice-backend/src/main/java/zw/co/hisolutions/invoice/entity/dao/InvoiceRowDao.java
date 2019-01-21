package zw.co.hisolutions.invoice.entity.dao;

import zw.co.hisolutions.invoice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository; 
 
public interface InvoiceRowDao extends JpaRepository<InvoiceRow, Long>{ 
    
}
