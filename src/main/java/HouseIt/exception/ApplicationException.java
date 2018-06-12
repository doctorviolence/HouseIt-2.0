package HouseIt.exception;

import org.apache.log4j.Logger;

public class ApplicationException extends RuntimeException {

    private final static Logger logger = Logger.getLogger(ApplicationException.class);

    public ApplicationException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public ApplicationException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}
