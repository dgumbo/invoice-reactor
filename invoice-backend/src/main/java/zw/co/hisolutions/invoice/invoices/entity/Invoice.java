package zw.co.hisolutions.invoice.invoices.entity;

import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import zw.co.hisolutions.common.entity.BaseEntity; 
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;

@Data
@Entity
public class Invoice extends BaseEntity{ 
    @ManyToOne(targetEntity = Header.class)
    private Header header ;
    
    @ManyToOne(targetEntity = Address.class)
    private Address shipTo ;
    
    @ManyToOne(targetEntity = Address.class)
    private Address billTo ;
    
    @OneToMany(targetEntity=InvoiceRow.class, cascade=CascadeType.ALL ) 
    private List<InvoiceRow> rows  ;
    
    private String notes; 
} 