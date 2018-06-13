package HouseIt.exception;

import org.apache.log4j.Logger;

public class ServiceLayerException extends RuntimeException {

    private Logger logger = Logger.getLogger(ServiceLayerException.class);

    public ServiceLayerException() {
        super();
    }

    public ServiceLayerException(String msg) {
        super(msg);
        logger.error(msg);
    }

    public ServiceLayerException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg, t);
    }

}