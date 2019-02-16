package zw.co.hisolutions.invoice.invoices.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.hisolutions.invoice.invoices.entity.Product;
 
public interface ProductDao extends JpaRepository<Product, Long>{ }