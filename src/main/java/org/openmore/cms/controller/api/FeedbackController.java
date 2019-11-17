package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.entity.Feedback;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.FeedbackDto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *   Created by org.openmore
 *      on 2019-05-01
*/
@Api(value = "/Feedback", tags = "Feedback", description = "用户反馈")
@RequestMapping(value = "/api/feedback", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class FeedbackController extends BaseController {
     private Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     private FeedbackService feedbackService;

     @TokenAuthCheck
     @ApiOperation(value = "分页返回所有数据", response = FeedbackDto.class, responseContainer = "List")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = FeedbackDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/query")
     public BaseResponse selectAllByPage(@RequestParam(required = false) @ApiParam(value = "用户id") String userId,
                                         @RequestParam(required = false) @ApiParam(value = "反馈内容") String content,
                                         @RequestParam(required = false) @ApiParam(value = "联系方式") String contact,
                                         @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize)
     {
          logger.debug(">> selectAllByPage");

          PageHelper.startPage(pageNum, pageSize);
          List<FeedbackDto> resultList = feedbackService.selectAll(userId, content, contact);
          if (resultList == null){
               throw new InvalidParamsException("没有数据");
          }
          PageInfo pageInfo = new PageInfo(resultList);
          return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
     }

     @TokenAuthCheck
     @ApiOperation(value = "根据id获取信息", response = FeedbackDto.class)
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/{id}")
     public BaseResponse getFeedbackById(@PathVariable @ApiParam(required = true, value = "id") String id){
        logger.debug(">> getFeedbackById");
        FeedbackDto feedbackDto = feedbackService.getDtoById(id);
        if (feedbackDto == null){
          throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
        }
        return BaseResponse.buildSuccessResponse(feedbackDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT)
     public BaseResponse updateFeedback(@RequestBody @ApiParam(value = "新信息", required = true) FeedbackDto feedback)
     {
        logger.debug(">> updateFeedback");
        Feedback entity = feedbackService.getEntityById(feedback.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        feedbackService.update(feedback);
        return BaseResponse.buildSuccessResponse("更新成功");
     }

     //@TokenAuthCheck
     @Transactional
     @ApiOperation("创建")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insertFeedback(@RequestBody @ApiParam(value = "创建", required = true) FeedbackDto feedback){
          logger.debug(">> insertFeedback");
        FeedbackDto feedbackDto = feedbackService.insert(feedback);
        return BaseResponse.buildSuccessResponse(feedbackDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "删除指定")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
     public BaseResponse deleteFeedback(@PathVariable @ApiParam(value = "id", required = true) String id)
     {
        logger.debug(">> deleteFeedback");
        feedbackService.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
     }
}




