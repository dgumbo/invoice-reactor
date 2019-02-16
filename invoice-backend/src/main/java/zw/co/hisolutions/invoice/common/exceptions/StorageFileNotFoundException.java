package zw.co.hisolutions.invoice.common.exceptions;

import java.net.MalformedURLException;

/**
 *
 * @author dgumbo
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message, MalformedURLException e) {
        super(message, e);
    }
    
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
