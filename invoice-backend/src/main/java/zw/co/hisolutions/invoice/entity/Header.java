package zw.co.hisolutions.invoice.entity;

import java.util.Date;
import java.text.SimpleDateFormat;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.hisolutions.common.entity.BaseEntity;

@Data
@Entity
public class Header extends BaseEntity{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invoiceDate;
    
    private String invoiceNumber; 
    

    public String getInvoiceDateString() {
        return sdf.format(this.invoiceDate);
    }

}
