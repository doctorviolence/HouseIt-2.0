package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordsDontMatchException extends Exception {

    private Logger logger = LoggerFactory.getLogger(PasswordsDontMatchException.class);

    public PasswordsDontMatchException() {
        super();
    }

    public PasswordsDontMatchException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public PasswordsDontMatchException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}