package org.openmore.common.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.entity.*;
import org.openmore.cms.entity.enums.LoginType;
import org.openmore.cms.entity.enums.Platform;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.*;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by michaeltang on 2017/7/30.
 */
public class UserRealm extends AuthorizingRealm implements IRealmClearCache{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Lazy
    private UserService userService;

    /**
     * 是否需要校验位置信息
     */
    public static final boolean NEED_LOCATION = false;


    @Override
    public String getName() {
        return "userRealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return super.getCredentialsMatcher();
    }

    /**
     * 自定义授权及权限管理,如果要实现自定授权及权限管理，需要继承：AuthorizingRealm
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //SuperUser superUser = (SuperUser) principals.getPrimaryPrincipal();
        logger.debug("> doGetAuthorizationInfo:" + principals.getPrimaryPrincipal().toString());
        return null;
    }

    /**
     * 清除缓存的登录信息和授权信息
     */
    public void clearCache(){
        Subject subject = SecurityUtils.getSubject();
        this.clearCachedAuthenticationInfo(subject.getPrincipals());
        this.clearCachedAuthorizationInfo(subject.getPrincipals());
    }

    /**
     * 用于验证需要授权的信息的合法性，如果信息不合法，则抛出异常
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        OpenmoreToken openmoreToken = (OpenmoreToken) token;
        if (openmoreToken.getType() == LoginType.SOCKET){
            String ip = ThreadLocalConfig.getIPAddr();
            String mac = ThreadLocalConfig.getMACAddr();
            String deviceToken = openmoreToken.getDeviceToken();
            Platform platform = UserAgentUtils.parseFromUserAgent(ThreadLocalConfig.getUserAgent());
            User user = new User();
            user.setDeviceToken(deviceToken);
            user.setIpAddress(ip);
            user.setMacAddress(mac);
            user.setPlatform(platform);
            user.setCreatedTime(new Date());
            String userNmuber = Lock.getOutTradeNumber(null);
            user.setHomeNumber(userNmuber);
            String userName = RandomName.randomName(new Random().nextBoolean(), 3);
            user.setNickName(userName);
            userService.insert(user);
            return this.userLogin(user, openmoreToken);
        } else {
            throw new OpenmoreException(4001, "非法登录！");
        }
    }

    /**
     * 重写账户密码校验方法(自己实现，不使用shiro校验)
     */
    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        return;
    }
    /**
     * user login
     */
    private SimpleAuthenticationInfo userLogin(User user, OpenmoreToken openmoreToken) {
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getLocked()) {
            throw new OpenmoreException(6002,
                    "账户被锁定请联系服务人员");
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getId(), //使用用户名，否则退出时，无法清楚session和登录信息
                user.getDeviceToken(), //密码
//                ByteSource.Util.bytes(user.getUuid()), //salt=username+salt
                new MySimpleByteSource(user.getDeviceToken()),
                getName() //realm name
        );
        if(!StringUtils.isEmpty(openmoreToken.getDeviceToken()) && !user.getDeviceToken().equals(openmoreToken.getDeviceToken())){
            user.setDeviceToken(openmoreToken.getDeviceToken());
            userService.update(user);
        }
        SecurityUtils.getSubject().getSession().setAttribute(user.getId(), JSON.toJSONString(user));
        ThreadLocalConfig.setUser(user);
        return authenticationInfo;
    }

    private String arrayToString(char[] arr){
        if(null == arr || arr.length<=0){
            return null;
        }
        // 遍历
        StringBuffer str5 = new StringBuffer();
        for (char s : arr) {
            str5.append(s);
        }
        return str5.toString();
    }
}
