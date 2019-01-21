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
public class ShippingData extends BaseEntity{

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private String shipNumber;
    private String salesRep;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shipDate;
    
    private String shipVia;
    private String terms;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate; 
    
    public String getShipDateString() {
        return sdf.format(this.shipDate);
    } 

    public String getDueDateString() {
        return sdf.format(this.dueDate);
    }
}
