package zw.co.hisolutions.invoice.receipts.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import zw.co.hisolutions.invoice.receipts.entity.Receipt;

/**
 *
 * @author dgumbo
 */
public interface ReceiptDao extends JpaRepository<Receipt, Long>{ }
