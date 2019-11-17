package org.openmore.cms.service;

import com.alibaba.fastjson.JSON;
import org.openmore.BaseTestCase;
import org.junit.runners.MethodSorters;
import org.openmore.cms.dto.api.AppVersionDto;
import org.openmore.cms.entity.AppVersion;
import org.openmore.cms.entity.enums.AppType;
import org.openmore.cms.service.AppVersionService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *
 * AppVersionService Tester.
 *   Created by org.openmore
 *      on 2019-04-30
 *
 * @author <Authors name>
 * @since <pre>2019-04-30</pre>
 * @version 1.0
 */
@Transactional
@Rollback
@FixMethodOrder(MethodSorters.JVM)
public class AppVersionServiceTest extends BaseTestCase{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppVersionService appVersionService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() throws Exception {
        loginWithTestToken();
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void otherTest() throws Exception {

    }


    /**
     *
     * Method: getAppVersionById(@PathVariable @ApiParam(required = true, value = "id") Integer id)
     *
     */
    @Test
    public void testSelectOneAppVersionById() throws Exception {
        assertTrue(appVersionService != null);
        AppVersionDto appVersionDto = appVersionService.getDtoById("test_id");
        assertTrue(appVersionDto != null);

        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
//        appVersionService.getDtoById("zzzz");
    }

    /**
     *
     * Method: queryByPage()
     *
     */
    @Test
    public void testSelectAllByPage() throws Exception {
        List<AppVersionDto> appVersionList = appVersionService.selectAll(null, null);
        assertTrue(appVersionList != null && appVersionList.size() >= 0);
    }


/**
 *
 * Method: update{className!""}(@PathVariable @ApiParam(value = "id", required = true) Integer id, @RequestBody @ApiParam(value = "新信息", required = true) AppVersion appVersion)
 *
 */
    @Test
    @Transactional
    @Rollback
    public void testUpdateAppVersion() throws Exception {
        AppVersionDto appVersion = appVersionService.getDtoById("test_id");
        assertTrue(appVersion != null);
        this.updateProperty(appVersion);
        appVersion.setId("test_id");
        appVersionService.update(appVersion);
        appVersion = appVersionService.getDtoById("test_id");
        assertTrue(appVersion != null);
        //System.out.println("==>appVersion:"+ JSON.toJSONString(appVersion));
        logger.info("==>appVersion:"+ JSON.toJSONString(appVersion));
        this.autoTestUpdate(appVersion);
    }

    /**
     *
     * Method: createAppVersion(@RequestBody @ApiParam(value = "创建", required = true) AppVersion appVersion)
     *
     */
    @Test
    public void testInsertAppVersion() throws Exception {
        AppVersionDto appVersion = new AppVersionDto();
        appVersion.setVersionCode("1.0");
        appVersion.setType(AppType.ANDROID_PAD);
        appVersion.setVersionName("1.0版本");
        this.updateProperty(appVersion);
        appVersionService.insert(appVersion);

        AppVersionDto appVersionDto = appVersionService.getDtoById(appVersion.getId());
        assertTrue(appVersionDto != null);
        // 测试是否抛出异常，该语句后面直接跟抛出异常的代码
        // thrown.expect(AlbusException.class);
    }


    /**
     *
     * Method: deleteAppVersion(@PathVariable @ApiParam(value = "id", required = true) Integer id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteAppVersion() throws Exception {
        appVersionService.deleteById("test_id2");
        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
        assertTrue(appVersionService.getEntityById("test_id2") == null);
    }
}


