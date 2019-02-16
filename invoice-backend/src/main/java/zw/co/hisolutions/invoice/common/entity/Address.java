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
        if (title != null && !title.trim().isEmpty()) {
            sb.append(getTitle()).append(" ");
        }
        if (firstname != null && !firstname.trim().isEmpty()) {
            sb.append(getFirstname()).append(" ");
        }
        if (lastname != null && !lastname.trim().isEmpty()) {
            sb.append(getLastname());
        }
        return sb.toString();
    }

    public boolean hasAddress1() {
        return address1 != null && !address1.trim().isEmpty();
    }

    public boolean hasAddress2() {
        return address2 != null && !address2.trim().isEmpty();
    }

    public boolean hasAddress3() {
        return address3 != null && !address3.trim().isEmpty();
    }

    public boolean hasCity() {
        return city != null && !city.trim().isEmpty();
    }

    public boolean hasCountry() {
        return country != null && !country.trim().isEmpty();
    }

    public boolean hasFullName() {
        return getFullName() != null && !getFullName().trim().isEmpty();
    }
}
