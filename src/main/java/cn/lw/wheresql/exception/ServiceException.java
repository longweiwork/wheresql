package cn.lw.wheresql.exception;

/**
 * @desc:
 * @author: longwei
 * @date: 2021/7/23
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 3583566093089790852L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
