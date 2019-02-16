package zw.co.hisolutions.invoice.common.service.impl;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service; 
import zw.co.hisolutions.invoice.common.api.ProductController;
import zw.co.hisolutions.invoice.invoices.entity.Product;
import zw.co.hisolutions.invoice.invoices.entity.dao.ProductDao;
import zw.co.hisolutions.invoice.common.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{ 

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }
    
    @Override
    public JpaRepository<Product, Long> getDao() {
        return productDao;
    }

    @Override
    public Class getController() {
        return ProductController.class;
    }
 } 
