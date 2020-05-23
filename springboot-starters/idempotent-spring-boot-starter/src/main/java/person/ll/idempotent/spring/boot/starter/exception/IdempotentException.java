package person.ll.idempotent.spring.boot.starter.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdempotentException extends RuntimeException{
    private final static Logger logger = LoggerFactory.getLogger(IdempotentException.class);

    private IdempotentException(String message) {
        super(message);
    }

    public IdempotentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotentException(Throwable cause) {
        super(cause);
    }

    private IdempotentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static IdempotentException error(String errorMsg){
        logger.error(errorMsg);
        return new IdempotentException(errorMsg);
    }

}
