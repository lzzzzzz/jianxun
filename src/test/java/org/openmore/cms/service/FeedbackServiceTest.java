package org.openmore.cms.service;

import org.openmore.BaseTestCase;
import org.junit.runners.MethodSorters;
import org.openmore.cms.dto.api.FeedbackDto;
import org.openmore.cms.entity.Feedback;
import org.openmore.cms.service.FeedbackService;
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
 * FeedbackService Tester.
 *   Created by org.openmore
 *      on 2019-05-01
 *
 * @author <Authors name>
 * @since <pre>2019-05-01</pre>
 * @version 1.0
 */
@Transactional
@Rollback
@FixMethodOrder(MethodSorters.JVM)
public class FeedbackServiceTest extends BaseTestCase{

    @Autowired
    private FeedbackService feedbackService;

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
     * Method: getFeedbackById(@PathVariable @ApiParam(required = true, value = "id") Integer id)
     *
     */
    @Test
    public void testSelectOneFeedbackById() throws Exception {
        assertTrue(feedbackService != null);
        FeedbackDto feedbackDto = feedbackService.getDtoById("test_id");
        assertTrue(feedbackDto != null);

        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
//        feedbackService.getDtoById("zzzz");
    }

    /**
     *
     * Method: queryByPage()
     *
     */
    @Test
    public void testSelectAllByPage() throws Exception {
        List<FeedbackDto> feedbackList = feedbackService.selectAll(null, null, null);
        assertTrue(feedbackList != null && feedbackList.size() >= 0);
    }


/**
 *
 * Method: update{className!""}(@PathVariable @ApiParam(value = "id", required = true) Integer id, @RequestBody @ApiParam(value = "新信息", required = true) Feedback feedback)
 *
 */
    @Test
    @Transactional
    @Rollback
    public void testUpdateFeedback() throws Exception {
        FeedbackDto feedback = feedbackService.getDtoById("test_id");
        assertTrue(feedback != null);
        this.updateProperty(feedback);
        feedback.setId("test_id");
        feedbackService.update(feedback);
        feedback = feedbackService.getDtoById("test_id");
        assertTrue(feedback != null);
        this.autoTestUpdate(feedback);
    }

    /**
     *
     * Method: createFeedback(@RequestBody @ApiParam(value = "创建", required = true) Feedback feedback)
     *
     */
    @Test
    public void testInsertFeedback() throws Exception {
        FeedbackDto feedback = new FeedbackDto();
        feedback.setUserId("test_id");
        this.updateProperty(feedback);
        feedbackService.insert(feedback);

        FeedbackDto feedbackDto = feedbackService.getDtoById(feedback.getId());
        assertTrue(feedbackDto != null);
        // 测试是否抛出异常，该语句后面直接跟抛出异常的代码
        // thrown.expect(AlbusException.class);
    }


    /**
     *
     * Method: deleteFeedback(@PathVariable @ApiParam(value = "id", required = true) Integer id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteFeedback() throws Exception {
        feedbackService.deleteById("test_id2");
        // 测试不存在的数据
        // 测试是否抛出异常
//        thrown.expect(Exception.class);
        assertTrue(feedbackService.getEntityById("test_id2") == null);
    }
}


