package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUploadException extends Exception {

    private Logger logger = LoggerFactory.getLogger(FileUploadException.class);

    public FileUploadException() {
        super();
    }

    public FileUploadException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public FileUploadException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}
