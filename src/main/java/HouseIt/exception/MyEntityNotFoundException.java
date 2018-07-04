package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyEntityNotFoundException extends Exception {

    private Logger logger = LoggerFactory.getLogger(MyEntityNotFoundException.class);

    public MyEntityNotFoundException() {
        super();
    }

    public MyEntityNotFoundException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public MyEntityNotFoundException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}