package zw.co.hisolutions.invoice.common.service.impl;
  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service; 
import zw.co.hisolutions.invoice.common.api.ShippingDataController;
import zw.co.hisolutions.invoice.common.entity.ShippingData;
import zw.co.hisolutions.invoice.common.entity.dao.ShippingDataDao;
import zw.co.hisolutions.invoice.common.service.ShippingDataService;

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
  