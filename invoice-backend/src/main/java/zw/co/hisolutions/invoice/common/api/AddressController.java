package zw.co.hisolutions.invoice.common.api;
  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.common.entity.Address; 
import zw.co.hisolutions.invoice.common.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController extends BasicRestController<Address, Long>{

    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    } 
    
    @Override
    public BasicService getService() {
        return addressService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
}