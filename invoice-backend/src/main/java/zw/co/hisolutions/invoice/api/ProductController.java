package zw.co.hisolutions.invoice.api;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService;
import zw.co.hisolutions.invoice.entity.Product;
import zw.co.hisolutions.invoice.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController extends BasicRestController<Product, Long>{ 

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    } 
    
    @Override
    public BasicService getService() {
        return productService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
 
 } 
