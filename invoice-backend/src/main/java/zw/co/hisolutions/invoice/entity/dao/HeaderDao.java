package zw.co.hisolutions.invoice.entity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.hisolutions.invoice.entity.Header;

public interface HeaderDao extends JpaRepository<Header, Long>{ } 
