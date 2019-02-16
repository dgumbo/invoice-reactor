package zw.co.hisolutions.invoice.common.storage.properties.impl;

import lombok.Data; 
import org.springframework.context.annotation.Configuration;
import zw.co.hisolutions.invoice.common.storage.properties.StorageProperties;

/**
 *
 * @author dgumbo
 */
@Configuration
@Data
//@Profile("development")
public class StoragePropertiesDevImpl implements StorageProperties { 
    
    //@Value("${server.upload.dir}")
    private String location = "" ;
     
}
