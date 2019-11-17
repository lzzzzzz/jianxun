package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/3/22.
 */
public class ForbiddenException extends OpenmoreException {
    public ForbiddenException() {
        this("无权访问，访问被拒绝");
    }
    public ForbiddenException(String msg) {
        super(4003, msg);
    }
}
