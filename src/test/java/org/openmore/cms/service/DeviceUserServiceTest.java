package org.openmore.cms.service;

import org.openmore.BaseTestCase;
import org.junit.runners.MethodSorters;
import org.openmore.cms.dto.api.DeviceUserDto;
import org.openmore.cms.entity.DeviceUser;
import org.openmore.cms.service.DeviceUserService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *
 * DeviceUserService Tester.
 *   Created by org.openmore
 *      on 2019-07-31
 *
 * @author <Authors name>
 * @since <pre>2019-07-31</pre>
 * @version 1.0
 */
@Transactional
@Rollback
@FixMethodOrder(MethodSorters.JVM)
public class DeviceUserServiceTest extends BaseTestCase{

    @Autowired
    private DeviceUserService deviceUserService;

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
     * Method: getDeviceUserById(@PathVariable @ApiParam(required = true, value = "id") Integer id)
     *
     */
    @Test
    public void testSelectOneDeviceUserById() throws Exception {
        assertTrue(deviceUserService != null);
        DeviceUserDto deviceUserDto = deviceUserService.getDtoById("test_id");
        assertTrue(deviceUserDto != null);

        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
//        deviceUserService.getDtoById("zzzz");
    }

    /**
     *
     * Method: queryByPage()
     *
     */
    @Test
    public void testSelectAllByPage() throws Exception {
        List<DeviceUserDto> deviceUserList = deviceUserService.selectAll(null);
        assertTrue(deviceUserList != null && deviceUserList.size() >= 0);
    }


/**
 *
 * Method: update{className!""}(@PathVariable @ApiParam(value = "id", required = true) Integer id, @RequestBody @ApiParam(value = "新信息", required = true) DeviceUser deviceUser)
 *
 */
    @Test
    @Transactional
    @Rollback
    public void testUpdateDeviceUser() throws Exception {
        DeviceUserDto deviceUser = deviceUserService.getDtoById("test_id");
        assertTrue(deviceUser != null);
        this.updateProperty(deviceUser);
        deviceUser.setId("test_id");
        deviceUserService.update(deviceUser);
        deviceUser = deviceUserService.getDtoById("test_id");
        assertTrue(deviceUser != null);
        this.autoTestUpdate(deviceUser);
    }

    /**
     *
     * Method: createDeviceUser(@RequestBody @ApiParam(value = "创建", required = true) DeviceUser deviceUser)
     *
     */
    @Test
    public void testInsertDeviceUser() throws Exception {
        DeviceUserDto deviceUser = new DeviceUserDto();
        this.updateProperty(deviceUser);
        deviceUserService.insert(deviceUser);

        DeviceUserDto deviceUserDto = deviceUserService.getDtoById(deviceUser.getId());
        assertTrue(deviceUserDto != null);
        // 测试是否抛出异常，该语句后面直接跟抛出异常的代码
        // thrown.expect(AlbusException.class);
    }


    /**
     *
     * Method: deleteDeviceUser(@PathVariable @ApiParam(value = "id", required = true) Integer id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteDeviceUser() throws Exception {
        deviceUserService.deleteById("test_id2");
        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
        assertTrue(deviceUserService.getEntityById("test_id2") == null);
    }
}


