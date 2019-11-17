package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.controller.common.BaseController;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import org.openmore.cms.service.OperateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.OperateLogDto;
import org.openmore.common.exception.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore
 * on 2018-08-22
 */
@Api(value = "/OperateLog", tags = "", description = "操作日志")
@RequestMapping(value = "/api/operateLog", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class OperateLogController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OperateLogService operateLogService;

    @TokenAuthCheck
    @ApiOperation(value = "分页返回所有数据", response = OperateLogDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = OperateLogDto.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/query")
    public BaseResponse selectAllByPage(@RequestParam(required = false, defaultValue = "0") @ApiParam(value = "操作人姓名") String name,
                                        @RequestParam(required = false, defaultValue = "0") @ApiParam(value = "开始时间") Date startTime,
                                        @RequestParam(required = false, defaultValue = "0") @ApiParam(value = "结束时间") Date endTime,
                                        @RequestParam(required = false, defaultValue = "0") @ApiParam(value = "分页第几页") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "0") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> query");
        PageHelper.startPage(pageNum, pageSize);
        List<OperateLogDto> resultList = operateLogService.search(name, startTime, endTime);
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        return BaseResponse.buildSuccessResponse(new Pagination(new PageInfo(resultList)), resultList);
    }

    @TokenAuthCheck
    @ApiOperation(value = "检索操作日志列表", response = OperateLogDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public BaseResponse searchOperate(@RequestParam(required = false) @ApiParam(value = "员工用户名") String staffName,
                                      @RequestParam(required = false) @ApiParam(value = "时间左边界 ") Date startTime,
                                      @RequestParam(required = false) @ApiParam(value = "时间右边界 ") Date endTime,
                                      @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                      @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> search");
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<OperateLogDto> resultList = operateLogService.search(staffName, startTime, endTime);
            if (resultList == null) {
                throw new InvalidParamsException("没有数据");
            }
            return BaseResponse.buildSuccessResponse(new Pagination(new PageInfo(resultList)), resultList);
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "查询数据失败");
        }
    }

    @TokenAuthCheck
    @ApiOperation(value = "根据id获取信息", response = OperateLogDto.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public BaseResponse getOperateLogById(@PathVariable @ApiParam(required = true, value = "id") String id) {
        logger.debug(">> getOperateLogById");
        try {
            OperateLogDto operateLogDto = operateLogService.getDtoById(id);
            if (operateLogDto == null) {
                throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
            }
            return BaseResponse.buildSuccessResponse(operateLogDto);
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "查询数据失败");
        }
    }
}




