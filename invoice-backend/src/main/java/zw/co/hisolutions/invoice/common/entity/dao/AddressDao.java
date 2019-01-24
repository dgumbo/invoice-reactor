package zw.co.hisolutions.invoice.common.entity.dao;
  
import org.springframework.data.jpa.repository.JpaRepository;  
import zw.co.hisolutions.invoice.common.entity.Address;
 
public interface AddressDao extends JpaRepository<Address, Long>{ 
    
}