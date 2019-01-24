package zw.co.hisolutions.invoice.receipt.service.impl;

import org.springframework.data.jpa.repository.JpaRepository; 
import zw.co.hisolutions.invoice.receipt.entity.Receipt;

/**
 *
 * @author dgumbo
 */
public interface ReceiptDao extends JpaRepository<Receipt, Long>{ }
