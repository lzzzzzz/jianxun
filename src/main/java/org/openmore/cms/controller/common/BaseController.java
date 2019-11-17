package org.openmore.cms.controller.common;

import org.openmore.cms.dto.common.BaseResponse;
import org.slf4j.Logger;
import org.openmore.common.exception.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 2017/6/16.
 */
@RestController
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpServletRequest request = null;
    @Autowired
    protected HttpServletResponse response = null;

    /**
     * 获得当前用户的权限范围
     *
     * @return
     */
    public String getScope() {
        return request.getHeader("X-SCOPE");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ExceptionHandler
    public BaseResponse handlerException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        if (ex instanceof InvalidParamsException) {
            InvalidParamsException exception = (InvalidParamsException) ex;
            return BaseResponse.buildFailResponse(-1, exception.getMsg());
        } else if (ex instanceof OpenmoreException) {
            OpenmoreException exception = (OpenmoreException) ex;
            return BaseResponse.buildFailResponse(exception.getCode(), exception.getMsg());
        } else {
            if (ex.getCause() instanceof OpenmoreException) {
                OpenmoreException exception = (OpenmoreException) ex.getCause();
                return BaseResponse.buildFailResponse(exception.getCode(), exception.getMsg());
            } else {
                return BaseResponse.buildFailResponse(-1, ex.getMessage());
            }
        }
    }
}
