package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExistsException extends Exception {

    private Logger logger = LoggerFactory.getLogger(UserExistsException.class);

    public UserExistsException() {
        super();
    }

    public UserExistsException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public UserExistsException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}