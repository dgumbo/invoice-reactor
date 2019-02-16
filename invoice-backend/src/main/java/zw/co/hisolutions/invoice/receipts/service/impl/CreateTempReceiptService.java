package zw.co.hisolutions.invoice.receipts.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import zw.co.hisolutions.invoice.common.entity.Address;
import zw.co.hisolutions.invoice.common.entity.Header;
import zw.co.hisolutions.invoice.common.service.AddressService;
import zw.co.hisolutions.invoice.common.service.HeaderService;
import zw.co.hisolutions.invoice.common.service.ProductService;
import zw.co.hisolutions.invoice.invoices.entity.Product;
import zw.co.hisolutions.invoice.receipts.entity.Receipt;
import zw.co.hisolutions.invoice.receipts.entity.ReceiptLine;
import zw.co.hisolutions.invoice.receipts.service.ReceiptService;

/**
 *
 * @author dgumbo
 */
@Service
public class CreateTempReceiptService {

    private final HeaderService headerService;
    private final ProductService productService;
    private final AddressService addressService;

    public CreateTempReceiptService(HeaderService headerService, ProductService productService, AddressService addressService) {
        this.headerService = headerService;
        this.productService = productService;
        this.addressService = addressService;
    }

    public Receipt create(ReceiptService receiptService, Receipt receipt) {
        if (receiptService.findAll().isEmpty()) {
            Header header = receipt.getHeader();
            Address billingAddress = receipt.getBillTo();

            billingAddress = addressService.save(billingAddress);
            header = headerService.save(header);
            
            receipt.setBillTo(billingAddress);
            receipt.setHeader(header);
 
            for (ReceiptLine row : receipt.getReceiptLines()) {
                Product product = row.getProduct();
                product = saveOrRetrieveProduct(product);
                row.setProduct(product);
                row.setReceipt(receipt);
            }

            receipt = receiptService.save(receipt);
        } else {
            receipt = receiptService.findAll().get(0);
        }
        return receipt;
    }

    private Product saveOrRetrieveProduct(Product product) {
        String prodName = product.getName();
        Random r = new Random();
        int lowerBound = 1000000000;
        int upperBound = 1999999999;

        Integer result = r.nextInt(upperBound - lowerBound) + lowerBound;
        String number = result.toString();

        Optional<Product> prodOpt = productService.findAll().stream()
                .filter(prod -> prod.getName().equals(prodName))
                .findAny();

        if (prodOpt.isPresent()) {
            product = prodOpt.get();
        } else { 
            product.setNumber(number); 
            product = productService.save(product);
        }

        return product;
    }
}
