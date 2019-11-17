package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/3/22.
 */
public class InvalidParamsException extends OpenmoreException {

    public InvalidParamsException() {
        this("请求参数不正确");
    }
    public InvalidParamsException(String msg) {
        super(4002, msg);
    }
}
