package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.entity.AppVersion;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.openmore.cms.entity.enums.AppType;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.service.AppVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.AppVersionDto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *   Created by org.openmore
 *      on 2019-04-30
*/
@Api(value = "/AppVersion", tags = "AppVersion", description = "版本控制")
@RequestMapping(value = "/api/appVersion", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class AppVersionController extends BaseController {
     private Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     private AppVersionService appVersionService;

     @TokenAuthCheck
     @ApiOperation(value = "分页返回所有数据", response = AppVersionDto.class, responseContainer = "List")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = AppVersionDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/query")
     public BaseResponse selectAllByPage(@RequestParam(required = false) @ApiParam(value = "版本code") String versionCode,
                                         @RequestParam(required = false) @ApiParam(value = "版本类型") AppType type,
                                         @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize)
     {
          logger.debug(">> selectAllByPage");

          PageHelper.startPage(pageNum, pageSize);
          List<AppVersionDto> resultList = appVersionService.selectAll(versionCode,type);
          if (resultList == null){
               throw new InvalidParamsException("没有数据");
          }
          PageInfo pageInfo = new PageInfo(resultList);
          return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
     }

     @TokenAuthCheck
     @ApiOperation(value = "根据id获取信息", response = AppVersionDto.class)
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = AppVersionDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/{id}")
     public BaseResponse getAppVersionById(@PathVariable @ApiParam(required = true, value = "id") String id){
        logger.debug(">> getAppVersionById");
        AppVersionDto appVersionDto = appVersionService.getDtoById(id);
        if (appVersionDto == null){
          throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
        }
        return BaseResponse.buildSuccessResponse(appVersionDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT)
     public BaseResponse updateAppVersion(@RequestBody @ApiParam(value = "新信息", required = true) AppVersionDto appVersion)
     {
        logger.debug(">> updateAppVersion");
        AppVersion entity = appVersionService.getEntityById(appVersion.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        appVersionService.update(appVersion);
        return BaseResponse.buildSuccessResponse("更新成功");

     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("创建")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = AppVersionDto.class) })
     @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insertAppVersion(@RequestBody @ApiParam(value = "创建", required = true) AppVersionDto appVersion){
          logger.debug(">> insertAppVersion");
        AppVersionDto appVersionDto = appVersionService.insert(appVersion);
        return BaseResponse.buildSuccessResponse(appVersionDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "删除指定")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
     public BaseResponse deleteAppVersion(@PathVariable @ApiParam(value = "id", required = true) String id)
     {
        logger.debug(">> deleteAppVersion");
        appVersionService.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
     }

     //@TokenAuthCheck
     @Transactional
     @ApiOperation(value = "版本检查")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = AppVersionDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/check/{versionCode}")
     public BaseResponse checkAppVersion(@PathVariable @ApiParam(value = "versionCode", required = true) String versionCode,
                                         @RequestParam() @ApiParam(value = "版本类型", required = true) AppType type)
     {
        logger.debug(">> deleteAppVersion");
        AppVersionDto appVersionDto = appVersionService.checkAppVersion(versionCode, type);
        if(null!= appVersionDto){
            return BaseResponse.buildSuccessResponse(appVersionDto);
        }else{
            return BaseResponse.buildSuccessResponse("已经是最新版本");
        }
     }


}




