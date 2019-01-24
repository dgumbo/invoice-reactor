package zw.co.hisolutions.invoice.common.entity;
 
import javax.persistence.Entity;
import lombok.Data; 
import zw.co.hisolutions.common.entity.BaseEntity;

@Data
@Entity
public class Address extends BaseEntity { 
    private String title;
    private String firstname;
    private String lastname;
    private String address1;
    private String address2;
    private String address3;
    private String city; 
    private String country; 

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getTitle()).append(" ");
        sb.append(this.getFirstname()).append(" ");
        sb.append(this.getLastname());
        return sb.toString();
    } 

    public boolean hasAddress2() {
        return this.address2 != null && !this.address2.trim().isEmpty();
    } 

    public boolean hasAddress3() {
        return this.address3 != null && !this.address3.trim().isEmpty();
    }  
}
