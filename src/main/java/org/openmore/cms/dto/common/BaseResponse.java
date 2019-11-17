package org.openmore.cms.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

/**
 * Created by michael on 2017/6/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

    @ApiModelProperty(value = "数据")
    public T data;

    @ApiModelProperty(value = "返回信息")
    public String msg;

    @ApiModelProperty(value = "返回码，正常返回0")
    public int code;

    @ApiModelProperty(value = "分页信息")
    public Pagination pagination;


    public static <T> BaseResponse<T> buildResponse(int code, String msg, T data, Pagination pagination) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.setCode(code);
        baseResponse.setData(data);
        baseResponse.setMsg(msg);
        baseResponse.setPagination(pagination);
        return baseResponse;
    }

    public static <T> BaseResponse<T> buildSuccessResponse(Pagination pagination, @Nullable T data) {
        return buildResponse(200, "ok", data, pagination);
    }

    public static <T> BaseResponse<T> buildSuccessResponse(@Nullable T data) {
        return buildResponse(200, "ok", data, null);
    }


    public static <T> BaseResponse<T> buildFailResponse(int statusCode, String msg) {
        return buildResponse(statusCode, msg, null, null);
    }


    public ResponseEntity toResponseEntity() {
        return ResponseEntity.status(code).body(this);
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
