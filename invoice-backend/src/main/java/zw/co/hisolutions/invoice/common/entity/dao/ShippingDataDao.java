package zw.co.hisolutions.invoice.common.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository; 
import zw.co.hisolutions.invoice.common.entity.ShippingData;

public interface ShippingDataDao extends JpaRepository<ShippingData, Long>{ 
    
}
 
