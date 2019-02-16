 package   zw.co.hisolutions.invoice.receipts.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private Header header ; 
    
    @ManyToOne(targetEntity = Address.class)
    @NotNull
    private Address billTo ;
    
    @OneToMany(targetEntity=ReceiptLine.class, cascade=CascadeType.ALL )  
    private List<ReceiptLine> receiptLines  ;
    
    @NotNull
    @Lob
    private String endNotes; 
}
