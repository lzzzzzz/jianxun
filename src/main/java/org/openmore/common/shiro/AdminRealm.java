/*
package org.openmore.common.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.cms.dto.api.GisInfo;
import org.openmore.cms.dto.api.OperateLogDto;
import org.openmore.cms.entity.*;
import org.openmore.cms.entity.enums.LoginType;
import org.openmore.cms.entity.enums.UserType;
import org.openmore.cms.service.*;
import org.openmore.common.exception.ForbiddenException;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by michaeltang on 2017/7/30.
 *//*

public class AdminRealm extends AuthorizingRealm implements IRealmClearCache{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Lazy
    private DictionaryService dictionaryService;

    @Autowired
    @Lazy
    private OperateLogService operateLogService;

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public String getName() {
        return "adminRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return super.getCredentialsMatcher();
    }

    */
/**
     * 自定义授权及权限管理,如果要实现自定授权及权限管理，需要继承：AuthorizingRealm
     *
     * @param principals
     * @return
     *//*

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //SuperUser superUser = (SuperUser) principals.getPrimaryPrincipal();
        logger.debug("> doGetAuthorizationInfo:" + principals.getPrimaryPrincipal().toString());
        try {
            User user = ThreadLocalConfig.getUser();
            if (user.isRoot()) {
                return addPermission(user);
            }
        } catch (Exception e) {
        }
        return null;
    }

    */
/**
     * 清除缓存的登录信息和授权信息
     *//*

    public void clearCache(){
        Subject subject = SecurityUtils.getSubject();
        this.clearCache(subject.getPrincipals());
    }

    */
/**
     * 用于验证需要授权的信息的合法性，如果信息不合法，则抛出异常
     *
     * @param token
     * @return
     * @throws AuthenticationException
     *//*

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OpenmoreToken openmoreToken = (OpenmoreToken) token;
        if (openmoreToken.getType() == LoginType.PASSWORD) {//密码登录
            return passwordLogin(openmoreToken);
        } else if(openmoreToken.getType() == LoginType.CAPTCHA){
            return phoneLogin(openmoreToken);
        }else if (openmoreToken.getType() == LoginType.WECHAT || openmoreToken.getType() == LoginType.WEIBO
                || openmoreToken.getType() == LoginType.QQ || openmoreToken.getType() == LoginType.MINIAPP) {//微信登录
            return wechatLogin(openmoreToken);
        } else {
            throw new OpenmoreException(4001, "非法登录！");
        }
    }

    */
/**
     * 重写账户密码校验方法(自己实现，不使用shiro校验)
     *//*

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        return;
    }

    */
/**
     * 微信登录
     *//*

    private SimpleAuthenticationInfo wechatLogin(OpenmoreToken openmoreToken) {
        String openId = openmoreToken.getUsername();
        SimpleAuthenticationInfo authenticationInfo = null;
        User user = userService.getByOpenid(openmoreToken.getType(), openId);
        // 工作人员用户
        if (!StringUtils.isEmpty(user.getId())) {
            User staff = userService.getEntityById(user.getId());
            authenticationInfo =  userLogin(staff, openmoreToken);
        }
        return authenticationInfo;
    }

    */
/**
     * 手机号码登录
     *//*

    private SimpleAuthenticationInfo phoneLogin(OpenmoreToken openmoreToken) {
        User user = userService.getUserByPhone(openmoreToken.getUsername());
        if (null != user) {
            return userLogin(user, openmoreToken);
        }
        throw new OpenmoreException(4001, "账户不存在");
    }

    */
/**
     * 手机号密码校验
     *//*

    private SimpleAuthenticationInfo passwordLogin(OpenmoreToken openmoreToken) {
        User user = userService.getUserByPhone(openmoreToken.getUsername());
        if(user == null) {
            throw new OpenmoreException(4000, "账户：" + openmoreToken.getUsername() + "不存在");
        }
        if (!userService.validatePassword(user, new String(openmoreToken.getPassword()))) {
            throw new IncorrectCredentialsException("密码不正确");
        }
        return userLogin(user, openmoreToken);

    }


    */
/**
     * staff login
     *//*

    private SimpleAuthenticationInfo userLogin(User staff, OpenmoreToken openmoreToken) {
        if (staff == null) {
            throw new UnknownAccountException("用户不存在");
        }
        logger.debug("==>staff login:" + staff.toString());
//      带有位置信息，需要判断
        if (openmoreToken.getLocation() != null) {
            // 如果是员工登陆，需要验证位置
            if (staff.getUserType().equals("STAFF")) {
                List<DictionaryDto> location = dictionaryService.getByParent("AN_QUAN_WEI_LAN", null, true);
                if (location == null || location.size() == 0) {
                    //throw new OpenmoreException("社区未配置安全登录围栏");
                    logger.debug("社区未配置安全登录围栏");
                } else {
                    GisInfo.Point geoFenching = null;
                    try {
                        geoFenching = JSON.parseObject(location.get(0).getData(), GisInfo.Point.class);
                    } catch (Exception e) {
                        throw new OpenmoreException("社区安全登录围栏配置错误");
                    }
                    // 非微信登录，需要进行围栏安全检查
                    if (openmoreToken.getType() != LoginType.WECHAT) {
                        if (geoFenching != null && geoFenching.getLat() > 0.0 && geoFenching.getLng() > 0.0
                                && !CommonUtils.isGeoInCircle(1000, geoFenching.getLat(), geoFenching.getLng(), openmoreToken.getLocation().getLat(), openmoreToken.getLocation().getLng())) {
                            staff.setLocked(true);
                            userService.update(staff);
                            //  记录日志
                            OperateLogDto dto = new OperateLogDto();
                            dto.setContent("安全登录范围外尝试登录系统，账号已经锁定，位置：" + location.get(0).getData());
                            dto.setIpAddress(ThreadLocalConfig.getIPAddr());
                            dto.setStaffId(staff.getId());
                            operateLogService.insert(dto);
                            throw new OpenmoreException(6001, "安全登录范围外无法登录系统！");
                        }
                    }
                }
            }
        }
        if (staff.getLocked()) {
            throw new OpenmoreException(6002, "系统检测到您的账号状态异常，需要通过手机号验证后登陆，短信将发送到：" + CommonUtils.replacePhoneNumber(staff.getPhone()));
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                openmoreToken.getUsername(), // 使用用户名，否则退出时，无法清楚session和登录信息
                staff.getPassword(), //密码
//                ByteSource.Util.bytes(user.getUuid()), //salt=username+salt
                new MySimpleByteSource(staff.getSalt()),
                getName() //realm name
        );
        // 员工和用户统一登录后，怕使用用户名作为sessionId会重复，使用id作为sessionId
        SecurityUtils.getSubject().getSession().setAttribute(openmoreToken.getUsername(), JSON.toJSONString(staff));
        ThreadLocalConfig.setUser(staff);
        return authenticationInfo;
    }

    */
/**
     * save staff permission and role
     *//*

    private SimpleAuthorizationInfo addPermission(User user) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }
}
*/
