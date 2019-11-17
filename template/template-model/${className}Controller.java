package ${basepackage!""}.${subpackage!""};

import io.swagger.annotations.*;
import ${basepackage!""}.controller.common.BaseController;
import ${basepackage!""}.entity.${className!"*"};

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.springframework.beans.BeanUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.annotation.TokenAuthCheck;
import ${basepackage!""}.service.${className!""}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${basepackage!""}.dto.api.${className!""}Dto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *   Created by ${author!"org.openmore"}
 *      on ${.now?string("yyyy-MM-dd")}
*/
@Api(value = "/${className!""}", tags = "${className_zn!""}", description = "${controller_desc!""}")
@RequestMapping(value = "/api/${(className!"")?uncap_first}", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class ${className!""}Controller extends BaseController {
     private Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     private ${className!""}Service ${(className!"")?uncap_first}Service;

     @TokenAuthCheck
     @ApiOperation(value = "分页返回所有数据", response = ${className!""}Dto.class, responseContainer = "List")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的${className_zn!""}", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/query")
     public BaseResponse selectAllByPage(@RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize)
     {
          logger.debug(">> selectAllByPage");

          PageHelper.startPage(pageNum, pageSize);
          List<${className!""}Dto> resultList = ${(className!"")?uncap_first}Service.selectAll();
          if (resultList == null){
               throw new InvalidParamsException("没有数据");
          }
          PageInfo pageInfo = new PageInfo(resultList);
          return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
     }

     @TokenAuthCheck
     @ApiOperation(value = "根据id获取${className_zn!""}信息", response = ${className!""}Dto.class)
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的${className_zn!""}", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/{id}")
     public BaseResponse get${className!""}ById(@PathVariable @ApiParam(required = true, value = "${className_zn!""}id") String id){
        logger.debug(">> get${className!''}ById");
        ${className!""}Dto ${(className!"")?uncap_first}Dto = ${(className!"")?uncap_first}Service.getDtoById(id);
        if (${(className!"")?uncap_first}Dto == null){
          throw new InvalidParamsException("请求失败：找不到id=" + id + "的${className_zn!""}");
        }
        return BaseResponse.buildSuccessResponse(${(className!"")?uncap_first}Dto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新${className_zn!""}")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT)
     public BaseResponse update${className!""}(@RequestBody @ApiParam(value = "新${className_zn!""}信息", required = true) ${className!""}Dto ${(className!"")?uncap_first})
     {
        logger.debug(">> update${className!""}");
        ${className!""} entity = ${(className!"")?uncap_first}Service.getEntityById(${(className!"")?uncap_first}.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        ${(className!"")?uncap_first}Service.update(${(className!"")?uncap_first});
        return BaseResponse.buildSuccessResponse("更新成功");

     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("创建${className_zn!""}")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insert${className!""}(@RequestBody @ApiParam(value = "创建${className_zn!""}", required = true) ${className!""}Dto ${(className!"")?uncap_first}){
          logger.debug(">> insert${className!''}");
        ${className!""}Dto ${(className!"")?uncap_first}Dto = ${(className!"")?uncap_first}Service.insert(${(className!"")?uncap_first});
        return BaseResponse.buildSuccessResponse(${(className!"")?uncap_first}Dto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "删除指定${className_zn!""}")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
     public BaseResponse delete${className!""}(@PathVariable @ApiParam(value = "${className_zn!""}id", required = true) String id)
     {
        logger.debug(">> delete${className!""}");
        ${(className!"")?uncap_first}Service.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
     }
}




