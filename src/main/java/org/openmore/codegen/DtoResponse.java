package org.openmore.codegen;

/**
 * Created by LZ on 2017/7/10.
 */
public class DtoResponse {

    /**
     * Success code(成功状态码)
     */
    public static final int RESPONSE_CODE_SUCCESS = 0x01;
    /**
     * Errorcode(失败状态码)
     */
    public static final int RESPONSE_CODE_ERROR = 0x02;

    /**
     * 结果状态
     */
    private static int responseCode = RESPONSE_CODE_ERROR;
    /**
     * 包含的异常
     */
    private Exception e;
    /**
     * 返回结果数据
     */
    private String responseData;

    public DtoResponse(Exception e){
        this.responseCode = RESPONSE_CODE_ERROR;
        this.e = e;
    }
    public DtoResponse(String responseData){
        this.responseCode = RESPONSE_CODE_SUCCESS;
        this.responseData = responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
