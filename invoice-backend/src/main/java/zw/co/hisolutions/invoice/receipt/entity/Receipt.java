 package   zw.co.hisolutions.invoice.receipt.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import zw.co.hisolutions.common.entity.BaseEntity;
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header; 

/**
 *
 * @author dgumbo
 */
@Data
@Entity
public class Receipt extends BaseEntity {
    
    @ManyToOne(targetEntity = Header.class)
    private Header header ; 
    
    @ManyToOne(targetEntity = Address.class)
    private Address billTo ;
    
    @OneToMany(targetEntity=ReceiptRow.class, cascade=CascadeType.ALL ) 
    private List<ReceiptRow> rows  ;
    
    private String notes; 
}
