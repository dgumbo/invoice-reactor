package zw.co.hisolutions.invoice.common.api;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService; 
import zw.co.hisolutions.invoice.common.entity.Header; 
import zw.co.hisolutions.invoice.common.service.HeaderService;

@RestController
@RequestMapping("/api/header")
public class HeaderController extends BasicRestController<Header, Long>{ 

    HeaderService headerService;

    public HeaderController(HeaderService headerService) {
        this.headerService = headerService;
    } 
    
    @Override
    public BasicService getService() {
        return headerService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
 
 } 
