package HouseIt.exception;

import org.apache.log4j.Logger;

public class HouseItServiceException extends Exception {

    private final static Logger logger = Logger.getLogger(HouseItServiceException.class);

    public HouseItServiceException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public HouseItServiceException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}
