package zw.co.hisolutions.invoice.receipts.api;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zw.co.hisolutions.common.controllers.rest.BasicRestController;
import zw.co.hisolutions.common.service.BasicService;
import zw.co.hisolutions.common.util.Results;
import zw.co.hisolutions.invoice.common.enums.ContentDisposalType;
import zw.co.hisolutions.invoice.receipts.entity.Receipt;
import zw.co.hisolutions.invoice.receipts.service.ReceiptService;

/**
 *
 * @author dgumbo
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/receipt")
public class ReceiptController extends BasicRestController<Receipt, Long> {

    private final String filename = "0101-single-receipt.pdf";
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public ResponseEntity<Receipt> create(@RequestBody Receipt receipt) throws Exception {
        System.out.println("\n" + receipt.getClass().getName() + "  B4 Save : " + receipt + "\n");

        ResponseEntity responseEntity;
        HttpStatus httpStatus;
        try {
            receipt = receiptService.create(receipt);

            httpStatus = HttpStatus.CREATED;
            responseEntity = new ResponseEntity<>(receipt, httpStatus);
        } catch (Exception ex) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            responseEntity = new ResponseEntity<>(new Results(Results.DBActionResult.EncounteredError, "Could not create entity.", "new", this.getClass()), httpStatus);

            //System.out.println(ex.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return responseEntity;
    }

    @ResponseBody
    @GetMapping("/view/{filenames:.+}") //using Path Variable
    public ResponseEntity<byte[]> viewFile(@PathVariable String filenames) throws IOException {
//    @GetMapping("/view") //using Path Variable
//    public ResponseEntity<Resource> viewFile( ) { 
        System.out.println("zw.co.hisolutions.storage.controllers.StorageController.viewFile()");
        return disposeFileContent(filename, ContentDisposalType.inline);
    }

    @ResponseBody
    @GetMapping("/view") //using Path Variable
    public ResponseEntity<byte[]> viewFile2() throws IOException {
        System.out.println("zw.co.hisolutions.storage.controllers.StorageController.viewFile()");
        return disposeFileContent(filename, ContentDisposalType.inline);
    }

    private ResponseEntity<byte[]> disposeFileContent(String filenames, ContentDisposalType contentDisposalType) throws IOException {
        MediaType mediaType = MediaType.ALL;
        long contentLength = 0;

        Receipt receipt = receiptService.findAll().get(0);

        ByteArrayInputStream in = null;

        PDDocument pdfDocument = receiptService.printPDF(receipt);

        try { 
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfDocument.save(out);

            in = new ByteArrayInputStream(out.toByteArray());
            contentLength = out.size();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pdfDocument.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        try {
            String mimeType = receiptService.getMimeType(in);
            mediaType = MediaType.valueOf(mimeType);
//            System.err.println("\nmediaType :" + mediaType + "\n\n");
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .contentLength(contentLength)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposalType + "; filename=\"" + filename + "\"")
                .body(IOUtils.toByteArray(in));
    }

    @Override
    public BasicService getService() {
        return receiptService;
    }

    @Override
    public Class getControllerClass() {
        return this.getClass();
    }
}
