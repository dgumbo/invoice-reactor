package zw.co.hisolutions.invoice.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.hisolutions.invoice.entity.Product;
 
public interface ProductDao extends JpaRepository<Product, Long>{ }