package zw.co.hisolutions.invoice.service.impl;
  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service; 
import zw.co.hisolutions.invoice.common.api.AddressController;
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.dao.AddressDao;
import zw.co.hisolutions.invoice.common.service.AddressService;

@Service
public class AddressServiceImpl  implements AddressService{ 

    private final AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }
    
    @Override
    public JpaRepository<Address, Long> getDao() {
        return addressDao;
    }

    @Override
    public Class getController() {
        return AddressController.class;
    }
}