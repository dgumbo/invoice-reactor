package zw.co.hisolutions.invoice.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.hisolutions.invoice.entity.Invoice;
 
public interface InvoiceDao extends JpaRepository<Invoice, Long>{ }