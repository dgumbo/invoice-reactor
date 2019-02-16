package zw.co.hisolutions.invoice.init;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.common.service.AddressService;
import zw.co.hisolutions.invoice.common.service.HeaderService;
import zw.co.hisolutions.invoice.invoices.entity.Invoice;
import zw.co.hisolutions.invoice.invoices.entity.InvoiceRow;
import zw.co.hisolutions.invoice.invoices.entity.Product;
import zw.co.hisolutions.invoice.invoices.service.InvoiceService;
import zw.co.hisolutions.invoice.common.service.ProductService;

/**
 *
 * @author dgumbo
 */
@Service
public class InitTestData {

    private final HeaderService headerService;
    private final InvoiceService invoiceService;
    private final ProductService productService;
    private final AddressService addressService;

    public InitTestData(HeaderService headerService, InvoiceService invoiceService, ProductService productService, AddressService addressService) {
        this.headerService = headerService;
        this.invoiceService = invoiceService;
        this.productService = productService;
        this.addressService = addressService;
    }

    public Invoice initData() {

        Invoice invoice = null;
        if (invoiceService.findAll().isEmpty()) {
            Header header = initHeader();
            initCompanyAddress();
            Address billingAddress = initBillingAddress();
            String notes = initNotes();

            billingAddress = addressService.save(billingAddress);
            header = headerService.save(header);

            invoice = new Invoice();
            invoice.setBillTo(billingAddress); 
            invoice.setShipTo(billingAddress);
            invoice.setHeader(header);
            invoice.setNotes(notes);

            List<Product> products = initProductListData();
//        products.forEach(prod -> prod = productService.save(prod));

            List<InvoiceRow> rows = initInvoiceRowListData(products);
            for (InvoiceRow row : rows) {
                row.setInvoice(invoice);
            }
            invoice.setRows(rows);

            invoice = invoiceService.save(invoice);
        } else {
            invoice = invoiceService.findAll().get(0);
        }
        return invoice;
    }

    private Header initHeader() {
        Header header = new Header();
        header.setDate(new Date());
        header.setNumber("FD2000103");
        return header;
    }

    private void initCompanyAddress() {
    }

    private Address initBillingAddress() {
        Address shipToAddress = new Address();
        shipToAddress.setTitle("Mr.");
        shipToAddress.setFirstname("Denzil");
        shipToAddress.setLastname("Gumbo");

        shipToAddress.setAddress1("14657 Galloway Park");
        shipToAddress.setAddress2("");
        shipToAddress.setAddress3("");
        shipToAddress.setCity("Norton");
        shipToAddress.setCountry("Zimbabwe");
        return shipToAddress;
    }

    private List<InvoiceRow> initInvoiceRowListData(List<Product> products) {
        InvoiceRow ir1 = new InvoiceRow();
        ir1.setProduct(products.get(0));
        ir1.setQuantity(90L);
        ir1.setPrice(new BigDecimal("5.39"));

        InvoiceRow ir2 = new InvoiceRow();
        ir2.setProduct(products.get(1));
        ir2.setQuantity(75L);
        ir2.setPrice(new BigDecimal("30.72"));

        InvoiceRow ir3 = new InvoiceRow();
        ir3.setProduct(products.get(2));
        ir3.setQuantity(195L);
        ir3.setPrice(new BigDecimal("79.70"));

        List<InvoiceRow> rows = new ArrayList();
        rows.add(ir1);
        rows.add(ir2);
        rows.add(ir3);
        return rows;
    }

    private List<Product> initProductListData() {
        List<Product> products = productService.findAll();

        String number1 = "1000001105";
        if (!products.stream().anyMatch(prod -> prod.getNumber().equals(number1))) {
            Product prod1 = new Product();
            prod1.setNumber(number1);
            prod1.setName(number1);
            prod1.setDescription("HisOlut - Steamed Chow Mein");
            prod1 = productService.save(prod1);
            products.add(prod1);
        }

        String number2 = "1000001113";
        if (!products.stream().anyMatch(prod -> prod.getNumber().equals(number2))) {
            Product prod2 = new Product();
            prod2.setNumber(number2);
            prod2.setName(number2);
            prod2.setDescription("Tea - Honey Green Tea");
            prod2 = productService.save(prod2);
            products.add(prod2);
        }

        String number3 = "1000001121";
        if (!products.stream().anyMatch(prod -> prod.getNumber().equals(number3))) {
            Product prod3 = new Product();
            prod3.setNumber(number3);
            prod3.setName(number3);
            prod3.setDescription("Paper Towel Touchless");
            prod3 = productService.save(prod3);
            products.add(prod3);
        }

        return products;
    }

    private String initNotes() {
        String notes = "notes";

        return notes;
    }
}
