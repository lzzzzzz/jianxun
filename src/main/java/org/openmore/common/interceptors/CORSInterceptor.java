package org.openmore.common.interceptors;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.entity.User;
import org.openmore.common.utils.CommonUtils;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.utils.GetMacAddressUtils;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by michaeltang on 2017/8/13.
 */
public class CORSInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
//      设置全局的租户id和ip地址，通过session获得
//        String tenantId =  request.getHeader("X-TENANT");
//        if (StringUtils.isEmpty(tenantId)) {
//            tenantId = "MICHAEL_TANG";
//        }
        // 请求结束后，把线程缓存数据清除，防止数据污染
        ReferencedFieldProcessor.clearThreadLocalCache();
        String ipAddress = CommonUtils.getIPAddress(request);
        ThreadLocalConfig.setIPAddr(ipAddress);
        //ThreadLocalConfig.setMACAddr(GetMacAddressUtils.getMacAddress(ipAddress));
        ThreadLocalConfig.setUserAgent(request.getHeader("User-Agent"));
        ThreadLocalConfig.setDeviceToken(request.getHeader("X-DEVICE-TOKEN"));
        ThreadLocalConfig.setRequestHost(request.getHeader("Origin"));
        ThreadLocalConfig.setScope(request.getHeader("X-SCOPE"));

        try{
            // 获得登录信息
            Subject subject = SecurityUtils.getSubject();
            //如果用户已经登录，获得登录信息，保存起来
            if (subject != null && subject.isAuthenticated()) {
                String loginName = (String) SecurityUtils.getSubject().getPrincipal();
                String userString = (String) SecurityUtils.getSubject().getSession().getAttribute(loginName);
                User user = JSONObject.parseObject(userString, User.class);
                if (null != user) {
                    ThreadLocalConfig.setUser(user);
                }
            }
        } catch (Exception e) {}

//        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-DEVICE-TOKEN,X-APP-VERSION,X-APP_KEY,Authorization,X-SIGN,X-NONCE,X-ENCRYPT,X-TIMESTAMP,X-SCOPE");
        // 添加浏览器能获到到的Header(默认浏览器只能获得content-type，cache-control)
//        response.addHeader("Access-Control-Expose-Headers", "set-cookie");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
//      允许cookies跨域保存
        response.addHeader("Access-Control-Allow-Credentials", "true");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
//      必须清除，因为Web容器是线程共享的
        ThreadLocalConfig.setIPAddr(null);
        ThreadLocalConfig.setMACAddr(null);
        ThreadLocalConfig.setUser(null);
        ThreadLocalConfig.setUserAgent(null);
        ThreadLocalConfig.setDeviceToken(null);
        // 请求结束后，把线程缓存数据清除，防止数据污染
        ReferencedFieldProcessor.clearThreadLocalCache();
    }
}
