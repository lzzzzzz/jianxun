package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/3/22.
 */
public class InvalidTokenException extends OpenmoreException {
    public InvalidTokenException() {
        this("无效的请求Token");
    }
    public InvalidTokenException(String msg) {
        super(4003, msg);
    }
}
