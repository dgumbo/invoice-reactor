package zw.co.hisolutions;
 
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment; 
import zw.co.hisolutions.invoice.entity.Invoice;
import zw.co.hisolutions.invoice.init.InitTestData;
import zw.co.hisolutions.invoice.service.InvoiceService;

@SpringBootApplication(scanBasePackages = {"zw.co.hisolutions"})
public class InvoiceBackendApplication {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    InitTestData initTestData;

    //@SuppressWarnings("resource")
    public static void main(String[] args) {
        SpringApplication.run(InvoiceBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner testForEnvironmentVariables(Environment environment) {
        // com.microsoft.sqlserver.jdbc.SQLServerDriver sqlDriver;
        com.mysql.cj.jdbc.Driver mySqlDriver;

        return (args) -> {
            System.out.println("\n\n");
            System.out.println("SPRING_PROFILES_ACTIVE-: " + environment.getProperty("SPRING_PROFILES_ACTIVE"));
            System.out.println("\n\n");
        };
    }

    @Bean
    CommandLineRunner initStart() { 

        PDDocument pdfDocument = new PDDocument();
        try {  
            Invoice invoice = initTestData.initData( ); 
             
            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);
            PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);

            invoiceService.printPDF(pdfDocument, contents, invoice);

            pdfDocument.save("0101-single-invoice.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pdfDocument.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        return (args) -> {

        };
    }
}
