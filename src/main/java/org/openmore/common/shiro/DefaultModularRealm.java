package org.openmore.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.LoginType;
import org.openmore.cms.entity.enums.UserType;
import org.openmore.cms.service.UserService;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by michaeltang on 2019/3/7.
 */
public class DefaultModularRealm extends ModularRealmAuthenticator {

    @Autowired
    @Lazy
    private UserService userService;

    private Map<String, Realm> definedRealms;
    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();
        Realm realm = this.definedRealms.get("userRealm");
        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }
    /**
     * 判断realm是否为空
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new AuthenticationException("未配置Realm!");
        }
    }

    public void setDefinedRealms(Map<String, Realm> definedRealms) {
        this.definedRealms = definedRealms;
    }
}
