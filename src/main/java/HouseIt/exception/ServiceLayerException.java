package HouseIt.exception;

import org.apache.log4j.Logger;

public class ServiceLayerException extends Exception {

    private final static Logger logger = Logger.getLogger(ServiceLayerException.class);

    public ServiceLayerException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public ServiceLayerException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}
