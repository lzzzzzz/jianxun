package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/3/22.
 */
public class ServiceErrorException extends OpenmoreException {

    public ServiceErrorException() {
        this("当前服务不可用");
    }
    public ServiceErrorException(String msg) {
        super(4005, msg);
    }
}
