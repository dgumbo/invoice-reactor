package zw.co.hisolutions.invoice.common.service.impl;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service; 
import zw.co.hisolutions.invoice.common.api.HeaderController;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.common.entity.dao.HeaderDao;
import zw.co.hisolutions.invoice.common.service.HeaderService;

@Service
public class HeaderServiceImpl implements HeaderService{ 

    private final HeaderDao headerDao;

    public HeaderServiceImpl(HeaderDao headerDao) {
        this.headerDao = headerDao;
    }
    
    @Override
    public JpaRepository<Header, Long> getDao() {
        return headerDao;
    }

    @Override
    public Class getController() {
        return HeaderController.class;
    }
 } 
