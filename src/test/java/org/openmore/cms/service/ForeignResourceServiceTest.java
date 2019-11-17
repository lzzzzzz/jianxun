package org.openmore.cms.service;

import org.openmore.BaseTestCase;
import org.junit.runners.MethodSorters;
import org.openmore.cms.dto.api.ForeignResourceDto;
import org.openmore.cms.entity.ForeignResource;
import org.openmore.cms.entity.enums.ResourceForeignType;
import org.openmore.cms.service.ForeignResourceService;
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
 * ForeignResourceService Tester.
 *   Created by org.openmore
 *      on 2019-05-02
 *
 * @author <Authors name>
 * @since <pre>2019-05-02</pre>
 * @version 1.0
 */
@Transactional
@Rollback
@FixMethodOrder(MethodSorters.JVM)
public class ForeignResourceServiceTest extends BaseTestCase{

    @Autowired
    private ForeignResourceService foreignResourceService;

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
     * Method: getForeignResourceById(@PathVariable @ApiParam(required = true, value = "id") Integer id)
     *
     */
    @Test
    public void testSelectOneForeignResourceById() throws Exception {
        assertTrue(foreignResourceService != null);
        ForeignResourceDto foreignResourceDto = foreignResourceService.getDtoById("test_id");
        assertTrue(foreignResourceDto != null);

        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
//        foreignResourceService.getDtoById("zzzz");
    }

    /**
     *
     * Method: queryByPage()
     *
     */
    @Test
    public void testSelectAllByPage() throws Exception {
        List<ForeignResourceDto> foreignResourceList = foreignResourceService.selectAll(null, null, null);
        assertTrue(foreignResourceList != null && foreignResourceList.size() >= 0);
    }


/**
 *
 * Method: update{className!""}(@PathVariable @ApiParam(value = "id", required = true) Integer id, @RequestBody @ApiParam(value = "新信息", required = true) ForeignResource foreignResource)
 *
 */
    @Test
    @Transactional
    @Rollback
    public void testUpdateForeignResource() throws Exception {
        ForeignResourceDto foreignResource = foreignResourceService.getDtoById("test_id");
        assertTrue(foreignResource != null);
        this.updateProperty(foreignResource);
        foreignResource.setId("test_id");
        foreignResource.setForeignType(ResourceForeignType.BANNER);
        foreignResourceService.update(foreignResource);
        foreignResource = foreignResourceService.getDtoById("test_id");
        assertTrue(foreignResource != null);
        this.autoTestUpdate(foreignResource);
    }

    /**
     *
     * Method: createForeignResource(@RequestBody @ApiParam(value = "创建", required = true) ForeignResource foreignResource)
     *
     */
    @Test
    public void testInsertForeignResource() throws Exception {
        ForeignResourceDto foreignResource = new ForeignResourceDto();
        this.updateProperty(foreignResource);
        foreignResource.setForeignType(ResourceForeignType.BANNER);
        foreignResourceService.insert(foreignResource);

        ForeignResourceDto foreignResourceDto = foreignResourceService.getDtoById(foreignResource.getId());
        assertTrue(foreignResourceDto != null);
        // 测试是否抛出异常，该语句后面直接跟抛出异常的代码
        // thrown.expect(AlbusException.class);
    }


    /**
     *
     * Method: deleteForeignResource(@PathVariable @ApiParam(value = "id", required = true) Integer id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteForeignResource() throws Exception {
        foreignResourceService.deleteById("test_id2");
        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
        assertTrue(foreignResourceService.getEntityById("test_id2") == null);
    }
}


