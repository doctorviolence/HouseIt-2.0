package HouseIt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceLayerException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(ServiceLayerException.class);

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