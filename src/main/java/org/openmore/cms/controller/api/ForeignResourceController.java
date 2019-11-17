package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.entity.ForeignResource;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.openmore.cms.entity.enums.ResourceForeignType;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.service.ForeignResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.ForeignResourceDto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *   Created by org.openmore
 *      on 2019-05-02
*/
@Api(value = "/ForeignResource", tags = "", description = "资源关联操作")
@RequestMapping(value = "/api/foreignResource", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class ForeignResourceController extends BaseController {
     private Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     private ForeignResourceService foreignResourceService;

     @TokenAuthCheck
     @ApiOperation(value = "分页返回所有数据", response = ForeignResourceDto.class, responseContainer = "List")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = ForeignResourceDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/query")
     public BaseResponse selectAllByPage(@RequestParam(required = false) @ApiParam(value = "资源ID") String resourceId,
                                         @RequestParam(required = false) @ApiParam(value = "关联ID") String foreignId,
                                         @RequestParam(required = false) @ApiParam(value = "使用类型") ResourceForeignType foreignType,
                                         @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize)
     {
          logger.debug(">> selectAllByPage");

          PageHelper.startPage(pageNum, pageSize);
          List<ForeignResourceDto> resultList = foreignResourceService.selectAll(resourceId, foreignId, foreignType);
          if (resultList == null){
               throw new InvalidParamsException("没有数据");
          }
          PageInfo pageInfo = new PageInfo(resultList);
          return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
     }

     @TokenAuthCheck
     @ApiOperation(value = "根据id获取信息", response = ForeignResourceDto.class)
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/{id}")
     public BaseResponse getForeignResourceById(@PathVariable @ApiParam(required = true, value = "id") String id){
        logger.debug(">> getForeignResourceById");
        ForeignResourceDto foreignResourceDto = foreignResourceService.getDtoById(id);
        if (foreignResourceDto == null){
          throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
        }
        return BaseResponse.buildSuccessResponse(foreignResourceDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT)
     public BaseResponse updateForeignResource(@RequestBody @ApiParam(value = "新信息", required = true) ForeignResourceDto foreignResource)
     {
        logger.debug(">> updateForeignResource");
        ForeignResource entity = foreignResourceService.getEntityById(foreignResource.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        foreignResourceService.update(foreignResource);
        return BaseResponse.buildSuccessResponse("更新成功");

     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("创建")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insertForeignResource(@RequestBody @ApiParam(value = "创建", required = true) ForeignResourceDto foreignResource){
          logger.debug(">> insertForeignResource");
        ForeignResourceDto foreignResourceDto = foreignResourceService.insert(foreignResource);
        return BaseResponse.buildSuccessResponse(foreignResourceDto);
     }
     @TokenAuthCheck
     @Transactional
     @ApiOperation("批量创建关联资源")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.POST, value = "/list", consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insertForeignResources(@RequestParam @ApiParam(value = "关联课程/专题ID", required = true) String foreignId,
                                                @RequestParam @ApiParam(value = "资源ID集合", required = true) String[] resourceIds,
                                                @RequestParam(required = false) @ApiParam(value = "使用类型")ResourceForeignType resourceForeignType,
                                                @RequestParam(required = false) @ApiParam(value = "需要和resourceIds集合对应", required = true) String[] params){
          logger.debug(">> insertForeignResource");
        foreignResourceService.insertResourceList(foreignId, resourceForeignType, resourceIds, params);
        return BaseResponse.buildSuccessResponse("保存成功");
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "删除指定")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
     public BaseResponse deleteForeignResourceById(@PathVariable @ApiParam(value = "id", required = true) String id)
     {
        logger.debug(">> deleteForeignResourceById");
        foreignResourceService.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
     }

    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "删除指定")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
    @RequestMapping(method = RequestMethod.DELETE, value = "/foreignId/{fId}/resource/{rId}")
    public BaseResponse deleteForeignResource(@PathVariable @ApiParam(value = "外键id", required = true) String fId,
                                              @PathVariable @ApiParam(value = "资源id", required = true) String rId)
    {
        logger.debug(">> deleteForeignResource");
        foreignResourceService.deleteById(fId, rId);
        return BaseResponse.buildSuccessResponse("数据删除成功");
    }
}




