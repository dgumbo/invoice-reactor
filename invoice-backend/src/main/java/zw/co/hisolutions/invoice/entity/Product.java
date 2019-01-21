package zw.co.hisolutions.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import zw.co.hisolutions.common.entity.BaseEntity;

/**
 *
 * @author dgumbo
 */

@Data
@Entity
public class Product extends BaseEntity{ 
    
    @Column(name = "_number", nullable = false, unique = true)
    private String number;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    private String description;
}
