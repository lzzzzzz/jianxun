package org.openmore;

import org.apache.shiro.realm.Realm;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.util.ThreadState;
import org.openmore.common.shiro.AdminRealm;
import org.openmore.common.shiro.DefaultModularRealm;
import org.openmore.common.shiro.UserRealm;
import org.openmore.common.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CMSApplication.class)
@Transactional
public class BaseTestCase {

	private static ThreadState subjectThreadState;

	/**
	 * Allows subclasses to set the currently executing {@link Subject} instance.
	 *
	 * @param subject the Subject instance
	 */
	protected void setSubject(Subject subject) {
		clearSubject();
		subjectThreadState = createThreadState(subject);
		subjectThreadState.bind();
	}

	protected Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	protected ThreadState createThreadState(Subject subject) {
		return new SubjectThreadState(subject);
	}

	/**
	 * Clears Shiro's thread state, ensuring the thread remains clean for future test execution.
	 */
	protected void clearSubject() {
		doClearSubject();
	}

	private static void doClearSubject() {
		if (subjectThreadState != null) {
			subjectThreadState.clear();
			subjectThreadState = null;
		}
	}

	protected static void setSecurityManager(SecurityManager securityManager) {
		SecurityUtils.setSecurityManager(securityManager);
	}

	protected static SecurityManager getSecurityManager() {
		return SecurityUtils.getSecurityManager();
	}

	protected void logintWithToken(String username, String password) {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		//设置authenticator
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		securityManager.setAuthenticator(authenticator);

		//设置authorizer
		ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
		authorizer.setPermissionResolver(new WildcardPermissionResolver());
		securityManager.setAuthorizer(authorizer);

		List<Realm> realmList = new ArrayList<>();
		realmList.add(userRealm);
		realmList.add(adminRealm);
		securityManager.setRealms(realmList);
		Map<String, Realm> realmMap = new HashMap<>();
		realmMap.put("userRealm", userRealm);
		realmMap.put("adminRealm", adminRealm);
		modularRealmAuthenticator.setDefinedRealms(realmMap);
		setSecurityManager(securityManager);
		Subject subject = getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			//4、登录，即身份验证
			subject.login(token);
		} catch (AuthenticationException e) {
			//5、身份验证失败
		}
	}


	public void loginWithTestToken(){
		this.logintWithToken("michael", "123456");
	}

	@Autowired
	private DefaultModularRealm modularRealmAuthenticator;
	@Autowired
	private UserRealm userRealm;
	@Autowired
	private AdminRealm adminRealm;

	@Before
	public void before() throws Exception {
		String deviceToken = "test_device_tokne";
		this.logintWithToken(CommonUtils.getTokenByUserId(1, deviceToken, CommonUtils.SCOPE_APP), deviceToken);
	}

	@After
	public void after() throws Exception {
	}

	@AfterClass
	public static void tearDownShiro() {
		doClearSubject();
		try {
			SecurityManager securityManager = getSecurityManager();
			LifecycleUtils.destroy(securityManager);
		} catch (UnavailableSecurityManagerException e) {
			//we don't care about this when cleaning up the test environment
			//(for example, maybe the subclass is a unit test and it didn't
			// need a SecurityManager instance because it was using only
			// mock Subject instances)
		}
		setSecurityManager(null);
	}


	public String getMethodName(String filedName) throws Exception{
		byte[] items = filedName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	public void autoTestUpdate(Object oldObj) throws Exception{
		Class<?> clz = oldObj.getClass();
		// 获取实体类的所有属性，返回Field数组
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			// 如果类型是String
			String type = field.getGenericType().toString();
			if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
				field.setAccessible(true);
				if(!field.getName().equals("id") && !field.getName().equals("dicKey") && !field.getName().equals("tenantId")){
					Method m = oldObj.getClass().getMethod(
							"get" + getMethodName(field.getName()));
//					所有更新String字段都修改成了test_string
					assertTrue("test_string".equals(m.invoke(oldObj)));// 调用setter方法);
					return;
				}
			}
		}
	}

	private List<Field> getAllField(Class tempClass){
		List<Field> fieldList = new ArrayList<>() ;
		while (tempClass != null) {
			//当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
		}
		return fieldList;
	}

	public void updateProperty(Object object) throws Exception{
		Class<?> clz = object.getClass();
		// 获取实体类的所有属性，返回Field数组
		List<Field> fields = getAllField(clz);
		for (Field field : fields) {// --for() begin
			// 静态成员不处理
			if(Modifier.isStatic(field.getModifiers())){
				continue;
			}
			// 如果类型是String
			if (field.getGenericType().toString().equals(
					"class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
				field.setAccessible(true);
				Method m = object.getClass().getMethod(
						"set" + getMethodName(field.getName()), String.class);
				m.invoke(object, "test_string");// 调用setter方法
				continue;
			}
			// 如果类型是Integer
			if (field.getGenericType().toString().equals(
					"class java.lang.Integer")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), Integer.class);
				m.invoke(object, 2);
				continue;
			}
			// 如果类型是Integer
			if (field.getGenericType().toString().equals(
					"int")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), int.class);
				m.invoke(object, 3);
				continue;
			}
			if (field.getGenericType().toString().equals(
					"class java.lang.Double")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), Double.class);
				m.invoke(object, 4.0);
				continue;
			}
			if (field.getGenericType().toString().equals(
					"class java.lang.Boolean")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), Boolean.class);
				m.invoke(object, false);
				continue;
			}
			if (field.getGenericType().toString().equals(
					"boolean")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), boolean.class);
				m.invoke(object, false);
				continue;
			}
			if (field.getGenericType().toString().equals(
					"class java.util.Date")) {
				Method m = (Method) object.getClass().getMethod(
						"set" + getMethodName(field.getName()), Date.class);
				m.invoke(object, new Date());
				continue;
			}
		}
	}
}
