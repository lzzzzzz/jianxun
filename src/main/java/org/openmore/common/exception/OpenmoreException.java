package org.openmore.common.exception;

/**
 * Created by michaeltang on 2018/7/23.
 */
public class OpenmoreException extends RuntimeException
{
    private int code;
    private String msg;

    public OpenmoreException(String msg){
        super(msg);
        this.code = 400;
        this.msg = msg;
    }

    public OpenmoreException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
