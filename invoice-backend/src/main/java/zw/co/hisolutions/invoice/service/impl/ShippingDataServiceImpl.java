package zw.co.hisolutions.invoice.service.impl;
  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.api.ShippingDataController;
import zw.co.hisolutions.invoice.entity.ShippingData;
import zw.co.hisolutions.invoice.entity.dao.ShippingDataDao;
import zw.co.hisolutions.invoice.service.ShippingDataService;

@Service
public class ShippingDataServiceImpl implements ShippingDataService{ 

    private final ShippingDataDao shippingDataDao;

    public ShippingDataServiceImpl(ShippingDataDao shippingDataDao) {
        this.shippingDataDao = shippingDataDao;
    }
    
    @Override
    public JpaRepository<ShippingData, Long> getDao() {
        return shippingDataDao;
    }

    @Override
    public Class getController() {
        return ShippingDataController.class;
    }
    
}
  