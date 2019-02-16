package zw.co.hisolutions.invoice.common.entity;

import java.util.Date;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.hisolutions.common.entity.BaseEntity;

@Data
@Entity
public class Header extends BaseEntity {

    @Transient
    final SimpleDateFormat SDF = new SimpleDateFormat("dd MMMM yyyy");

    private String invoiceRef; // INV19010019

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "_date")
    private Date date = new Date();

    @Column(name = "_number")
    private String number;

//    public String getInvoiceDateString() {
//        return SDF.format(date);
//    }
}
