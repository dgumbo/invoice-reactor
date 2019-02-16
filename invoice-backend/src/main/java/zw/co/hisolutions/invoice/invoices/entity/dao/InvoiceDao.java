package zw.co.hisolutions.invoice.invoices.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import zw.co.hisolutions.invoice.invoices.entity.Invoice;
 
public interface InvoiceDao extends JpaRepository<Invoice, Long>{ }