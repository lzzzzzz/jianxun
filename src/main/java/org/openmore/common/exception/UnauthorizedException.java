package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/3/22.
 */
public class UnauthorizedException extends OpenmoreException {
    public UnauthorizedException() {
        this("无权访问，需要您登录");
    }
    public UnauthorizedException(String msg) {
        super(4001, msg);
    }
}
