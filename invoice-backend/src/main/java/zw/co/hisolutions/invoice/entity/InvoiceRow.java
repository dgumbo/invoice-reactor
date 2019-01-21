package zw.co.hisolutions.invoice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;
import zw.co.hisolutions.common.entity.BaseEntity;

@Data
@Entity
public class InvoiceRow extends BaseEntity{
    @JsonIgnore
    @ManyToOne(targetEntity = Invoice.class)
    private Invoice invoice;
     
    @ManyToOne(targetEntity = Product.class)
    @NotNull
    private Product product;
    
    private long quantity;
    private BigDecimal price;
  
    public BigDecimal addTotal(BigDecimal totalCost) {
        return totalCost.add(this.getTotal());
    }

    public String getTotalString() {
        BigDecimal printTotal = getTotal();
        printTotal.setScale(2, RoundingMode.HALF_EVEN);
        return printTotal.toString();
    }

    public String getPriceString() {
        BigDecimal printPrice = getPrice();
        printPrice.setScale(2, RoundingMode.HALF_EVEN);
        return printPrice.toString();
    }

    public String getQuantityString() {
        return Double.toString(quantity);
    }

    public BigDecimal getTotal() {
        return this.price.multiply(new BigDecimal(quantity));
    }  
}
