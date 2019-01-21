package zw.co.hisolutions.invoice.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.hisolutions.invoice.entity.ShippingData;

public interface ShippingDataDao extends JpaRepository<ShippingData, Long>{ 
    
}
 
