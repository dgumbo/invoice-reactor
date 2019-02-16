package zw.co.hisolutions.invoice.common.exceptions;

/**
 *
 * @author dgumbo
 */
public class StorageException  extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
