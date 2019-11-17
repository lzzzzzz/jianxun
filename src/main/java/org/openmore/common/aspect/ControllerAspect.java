package org.openmore.common.aspect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.openmore.cms.entity.User;
import org.openmore.common.exception.ForbiddenException;
import org.openmore.common.exception.InvalidTokenException;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.CommonUtils;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by michaeltang on 2017/7/31.
 */
@Aspect
@Component
public class ControllerAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 匹配指定包中的所有的方法
//    @Pointcut("execution(* com.xys.service.UserService.*(..))") // 切点表达式
//    private void dataAccessOperation() {} // 切点前面

    //Controller层切点
    @Pointcut("@annotation(org.openmore.common.annotation.SignatureCheck)")
    public void controllerSignAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(org.openmore.common.annotation.TokenAuthCheck)")
    public void controllerTokenAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(org.openmore.common.annotation.UserCheck)")
    public void controllerUserCheckAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(org.openmore.common.annotation.PermissionCheck)")
    public void controllerPermissionCheckAspect() {
    }

    //Controller层切点
    @Pointcut("@annotation(org.openmore.common.annotation.RoleCheck)")
    public void controllerRoleCheckAspect() {
    }

    @Around("controllerUserCheckAspect()")
    public Object doUserCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug(">> doUserCheck");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated()) {
                logger.error("==>ForbiddenException:用户未登录");
                throw new ForbiddenException("用户未登录");
            }
            User user = (User) subject.getPrincipal();
            // 获得用户的所有的角色
            //subject.checkRoles(roleService.getRolesByStaffId(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, 403, "没有访问权限");
            logger.error(">> ForbiddenException");
            return null;
        }
        return joinPoint.proceed();
    }

    private String getShortClassName(String fullClassName)
    {
        int idx = fullClassName.lastIndexOf(".");
        int controllerIdx = fullClassName.lastIndexOf("Controller");
        if (controllerIdx >= 0)
        {
            return fullClassName.substring(idx + 1, controllerIdx);
        } else {
            return fullClassName.substring(idx + 1);
        }
    }

    @Around("controllerPermissionCheckAspect()")
    public Object doPermissionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug(">> doPermissionCheck");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            Subject subject = SecurityUtils.getSubject();
            String controllerName = this.getShortClassName(joinPoint.getTarget().getClass().getName());
            logger.debug(">> check controller: " + controllerName);
            String methodName = joinPoint.getSignature().getName();
            logger.debug(">> check method: " + methodName);
            String permission = controllerName + ":" + methodName;
            subject.checkPermission(permission);
        } catch (Exception e) {
            sendResponse(response, 403, "没有访问权限");
            logger.error(">> UnauthenticatedException");
            return null;
        }
        return joinPoint.proceed();
    }

    @Around("controllerRoleCheckAspect()")
    public Object doRoleCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug(">> doRoleCheck");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            Subject subject = SecurityUtils.getSubject();
            logger.debug(">> check: " + joinPoint.getSignature().getName());
            subject.checkRole(joinPoint.getSignature().getName());
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, 403, "没有访问权限");
            logger.error(">> ForbiddenException");
            return null;
        }
        return joinPoint.proceed();
    }

    //    @Before("@annotation(org.openmore.common.annotation.SignatureCheck)")
    @Around("controllerTokenAspect()")
    public Object doTokenCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug(">> doTokenCheck");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            checkToken(request, response);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            sendResponse(response, e.getCode(),  e.getMsg());
            logger.error(">> ForbiddenException");
            return null;
        }
        return joinPoint.proceed();
    }

    /**
     * 检查签名
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    //    @Before("@annotation(org.openmore.common.annotation.SignatureCheck)")
    @Around("controllerSignAspect()")
    public Object doSignCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug(">> doSignCheck");
//        System.out.println(">> " + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            checkSignature(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(response, 403, e.getMessage());
            logger.error(">> ForbiddenException");
            return null;
        }
        return joinPoint.proceed();
    }


    /**
     * 检查用户参数里的授权码及设备Token
     *
     * @param request
     * @param response
     * @throws OpenmoreException
     */
    private void checkToken(HttpServletRequest request, HttpServletResponse response) throws InvalidTokenException {
        logger.debug("检查授权信息:{} {}", request.getMethod(), request.getRequestURI());

        Subject subject = SecurityUtils.getSubject();
        //如果用户已经授权，直接返回
        if (subject.isAuthenticated()) {
            User user = ThreadLocalConfig.getUser();
//            String json = (String) SecurityUtils.getSubject().getSession().getAttribute(subject.getPrincipal().toString());
//            logger.debug("==?>json:"+json);
            if (user == null) {
                throw new OpenmoreException(4001, "Session过期，请退出，重新登录");
            }
            return;
        } else {
            logger.error("该接口需要登录权限，未登录");
            throw new OpenmoreException( 4001, "该接口需要登录权限");
        }
    }

    /**
     * 检查签名
     *
     * @param request
     * @param response
     * @throws ForbiddenException
     */
    private void checkSignature(HttpServletRequest request, HttpServletResponse response) throws ForbiddenException {
        logger.debug("Accept:{}", request.getHeader("Accept"));
        logger.debug("Content-Type:{}", request.getHeader("Content-Type"));
        long startTime = System.currentTimeMillis();
        logger.debug("AOP拦截到请求:{} {}", request.getMethod(), request.getRequestURI());

        // ------- 开始校验 -------
        String sign = request.getHeader("X-SIGN");
        String time = request.getHeader("X-TIMESTAMP");
        String nonce = request.getHeader("X-NONCE");
        String key = request.getHeader("X-APP_KEY");
        String encrypt = request.getHeader("X-ENCRYPT");
        String contentType = request.getHeader("Content-Type");

        String jsonBody = "";

        logger.debug(jsonBody);

        if (StringUtils.isEmpty(encrypt) || encrypt == null) {
            return;
        }

        logger.debug(jsonBody);
        logger.debug("sign = {} time = {} nonce = {} key = {} jsonBody = {} encrypt = {}", sign, time, nonce, key, jsonBody, encrypt);

        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(time)
                || StringUtils.isEmpty(nonce) || StringUtils.isEmpty(key)
                || StringUtils.isEmpty(contentType)) {
            logger.error("Header缺少参数");
            throw new ForbiddenException("Header缺少参数");
        }

        if (!request.getHeader("Content-Type").contains("application/json")) {
            logger.error("Content-Type配置不正确");
            throw new ForbiddenException("Content-Type配置不正确");
        }

        if (Math.abs(startTime / 1000 - Long.parseLong(time)) > 60) {
            logger.debug("请求时间戳超过60秒");
            throw new ForbiddenException("请求时间戳超过60秒");
        }

        String secret = "";

        if ("app_android_openmore_001".equals(key)) {
            secret = "hahahaha";
        }

        String unsignString = secret + nonce + request.getMethod().toUpperCase() + request.getRequestURI() + jsonBody + time;
        String mysign = "";
        try {
            logger.debug("unsignString = {}", unsignString);
            mysign = CommonUtils.md5(unsignString);
            logger.debug("sign = {}", mysign);
        } catch (Exception e) {
            logger.debug("md5加密失败");
            throw new ForbiddenException("签名加密失败");
        }

        if (!sign.toUpperCase().equals(mysign.toUpperCase())) {
            logger.error("signature not corrected");
            throw new ForbiddenException("签名不正确");
        }

        long endTime = System.currentTimeMillis();
        logger.debug("请求: {} {}", request.getMethod(), request.getRequestURI());
        logger.debug("花费时间：" + (endTime - startTime) + "ms");
    }


    /**
     * 异常发送响应内容
     *
     * @param response
     * @param errorMsg
     */
    private void sendResponse(HttpServletResponse response, int code, String errorMsg) {
        ServletOutputStream out = null;
        String json = "";
        switch (code) {
            case 401: // 未登录，未授权
                json = "{\"msg\": \"" + errorMsg + "\",\"code\": 4001}";
                break;
            case 403: // 没有权限访问
                json = "{\"msg\": \"" + errorMsg + "\",\"code\": 4003}";
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json;charset=UTF-8");
            response.setStatus(code);
            out = response.getOutputStream();
            out.write(json.getBytes("UTF-8"));
        } catch (IOException e2) {
            logger.error(">> IOException");
            e2.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e3) {
                    logger.error(">> IOException");
                    e3.printStackTrace();
                }
            }
        }
    }

}
