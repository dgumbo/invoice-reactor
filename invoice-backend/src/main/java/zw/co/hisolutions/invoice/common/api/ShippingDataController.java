package zw.co.hisolutions.invoice.common.api;
  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.common.entity.ShippingData; 
import zw.co.hisolutions.invoice.common.service.ShippingDataService;

@RestController
@RequestMapping("/api/shipping-data")
public class ShippingDataController extends BasicRestController<ShippingData, Long>{  

    ShippingDataService ShippingDataService;

    public ShippingDataController(ShippingDataService ShippingDataService) {
        this.ShippingDataService = ShippingDataService;
    } 
    
    @Override
    public BasicService getService() {
        return ShippingDataService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
}
  