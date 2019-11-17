package ${basepackage!""}.${subpackage!""};

import org.openmore.BaseTestCase;
import org.junit.runners.MethodSorters;
import ${basepackage!""}.dto.api.${className!""}Dto;
import ${basepackage!""}.entity.${className!""};
import ${basepackage!""}.service.${className!""}Service;
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
 * ${className!""}Service Tester.
 *   Created by ${author!"org.openmore"}
 *      on ${.now?string("yyyy-MM-dd")}
 *
 * @author <Authors name>
 * @since <pre>${.now?string("yyyy-MM-dd")}</pre>
 * @version 1.0
 */
@Transactional
@Rollback
@FixMethodOrder(MethodSorters.JVM)
public class ${className!""}ServiceTest extends BaseTestCase{

    @Autowired
    private ${className!""}Service ${className?uncap_first}Service;

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
     * Method: get${className!""}ById(@PathVariable @ApiParam(required = true, value = "${className_zn!""}id") Integer id)
     *
     */
    @Test
    public void testSelectOne${className!""}ById() throws Exception {
        assertTrue(${className?uncap_first}Service != null);
        ${className}Dto ${className?uncap_first}Dto = ${className?uncap_first}Service.getDtoById("test_id");
        assertTrue(${className?uncap_first}Dto != null);

        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
//        ${className?uncap_first}Service.getDtoById("zzzz");
    }

    /**
     *
     * Method: queryByPage()
     *
     */
    @Test
    public void testSelectAllByPage() throws Exception {
        List<${className}Dto> ${className?uncap_first}List = ${className?uncap_first}Service.selectAll();
        assertTrue(${className?uncap_first}List != null && ${className?uncap_first}List.size() >= 0);
    }


/**
 *
 * Method: update{className!""}(@PathVariable @ApiParam(value = "${className_zn!""}id", required = true) Integer id, @RequestBody @ApiParam(value = "新${className_zn!""}信息", required = true) ${className!""} ${className?uncap_first})
 *
 */
    @Test
    @Transactional
    @Rollback
    public void testUpdate${className!""}() throws Exception {
        ${className}Dto ${className?uncap_first} = ${className?uncap_first}Service.getDtoById("test_id");
        assertTrue(${className?uncap_first} != null);
        this.updateProperty(${className?uncap_first});
        ${className?uncap_first}.setId("test_id");
        ${className?uncap_first}Service.update(${className?uncap_first});
        ${className?uncap_first} = ${className?uncap_first}Service.getDtoById("test_id");
        assertTrue(${className?uncap_first} != null);
        this.autoTestUpdate(${className?uncap_first});
    }

    /**
     *
     * Method: create${className!""}(@RequestBody @ApiParam(value = "创建${className_zn!""}", required = true) ${className!""} ${className?uncap_first})
     *
     */
    @Test
    public void testInsert${className!""}() throws Exception {
        ${className}Dto ${className?uncap_first} = new ${className}Dto();
        this.updateProperty(${className?uncap_first});
        ${className?uncap_first}Service.insert(${className?uncap_first});

        ${className}Dto ${className?uncap_first}Dto = ${className?uncap_first}Service.getDtoById(${className?uncap_first}.getId());
        assertTrue(${className?uncap_first}Dto != null);
        // 测试是否抛出异常，该语句后面直接跟抛出异常的代码
        // thrown.expect(AlbusException.class);
    }


    /**
     *
     * Method: delete${className!""}(@PathVariable @ApiParam(value = "${className_zn!""}id", required = true) Integer id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDelete${className!""}() throws Exception {
        ${className?uncap_first}Service.deleteById("test_id2");
        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
        assertTrue(${className?uncap_first}Service.getEntityById("test_id2") == null);
    }
}


