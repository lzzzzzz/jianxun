package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.entity.DeviceUser;
import org.openmore.cms.entity.User;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.openmore.cms.entity.enums.Platform;
import org.openmore.cms.entity.enums.UserType;
import org.openmore.cms.service.DeviceUserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.UserDto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 *   Created by org.openmore
 *      on 2019-04-30
*/
@Api(value = "/User", tags = "", description = "")
@RequestMapping(value = "/api/user", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class UserController extends BaseController {
     private Logger logger = LoggerFactory.getLogger(this.getClass());

     @Autowired
     private UserService userService;

     @Autowired
     private DeviceUserService deviceUserService;

     @TokenAuthCheck
     @ApiOperation(value = "分页返回所有数据", response = UserDto.class, responseContainer = "List")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/query")
     public BaseResponse selectAllByPage(@RequestParam(required = false) @ApiParam(value = "用户ID") String id,
                                         @RequestParam(required = false) @ApiParam(value = "用户名称")String username,
                                         @RequestParam(defaultValue = "USER") @ApiParam(value = "用户类型", required = true)UserType type,
                                         @RequestParam(required = false) @ApiParam(value = "是否是VIP")Boolean isVip,
                                         @RequestParam(required = false) @ApiParam(value = "手机号")String phone,
                                         @RequestParam(required = false) @ApiParam(value = "用户来源平台")Platform platform,
                                         @RequestParam(required = false) @ApiParam(value = "用户注册开始时间")Date startTime,
                                         @RequestParam(required = false) @ApiParam(value = "用户注册结束时间")Date endTime,
                                         @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                         @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize)
     {
          logger.debug(">> selectAllByPage");

          User user = ThreadLocalConfig.getUser();

          PageHelper.startPage(pageNum, pageSize);
          List<UserDto> resultList = null;//userService.selectAll(id, username, type, null, isVip, phone, platform, startTime, endTime);
          if (resultList == null){
               throw new InvalidParamsException("没有数据");
          }
          PageInfo pageInfo = new PageInfo(resultList);
          return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
     }

     //@TokenAuthCheck
     @ApiOperation(value = "根据id获取信息,id为用户ID或设备token", response = UserDto.class)
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的用户", response = UserDto.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/{id}")
     public BaseResponse getUserById(@PathVariable @ApiParam(required = true, value = "id") String id){
        logger.debug(">> getUserById");
         String userId=null;
         UserDto userDto = null;
         User user = ThreadLocalConfig.getUser();
         if(null!=user){
             userId = user.getId();
             userDto = userService.getDtoById(userId);
         }
         if(null == userDto){
             DeviceUser deviceUser = deviceUserService.getByToken(id);
             userId = deviceUser.getId();
             userDto = new UserDto();

         }
         if(StringUtils.isEmpty(userId)){
             return BaseResponse.buildFailResponse(400, "用户非法，请先登录");
         }
        if (userDto == null){
          throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
        }
        /*UserCoinDto userCoin = userCoinService.getByUserId(userDto.getId());
        if(null!= userCoin){
            userDto.setUserCoinCount(userCoin.getCoinNum());
        }*/
        return BaseResponse.buildSuccessResponse(userDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新：修改数据并更新缓存，用于自己修改自己数据")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT)
     public BaseResponse updateUser(@RequestBody @ApiParam(value = "新信息", required = true) UserDto user)
     {
        logger.debug(">> updateUser");
        User entity = userService.getEntityById(user.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        userService.update(user);
        return BaseResponse.buildSuccessResponse("更新成功");

     }
     @TokenAuthCheck
     @Transactional
     @ApiOperation("更新：修改数据不更新缓存，用于修改他人数据")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
                             @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.PUT, value = "/noCacheUpdate")
     public BaseResponse updateUserNoCache(@RequestBody @ApiParam(value = "新信息", required = true) User user)
     {
        logger.debug(">> updateUser");
        User entity = userService.getEntityById(user.getId());
        if (entity == null){
          throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        userService.updateNoCache(user);
        return BaseResponse.buildSuccessResponse("更新成功");

     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation("创建")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
     public BaseResponse insertUser(@RequestBody @ApiParam(value = "创建", required = true) UserDto user){
          logger.debug(">> insertUser");
        UserDto userDto = userService.insert(user);
        return BaseResponse.buildSuccessResponse(userDto);
     }

     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "删除指定")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
     public BaseResponse deleteUser(@PathVariable @ApiParam(value = "id", required = true) String id)
     {
        logger.debug(">> deleteUser");
        userService.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
     }
     @TokenAuthCheck
     @Transactional
     @ApiOperation(value = "用户禁言/解禁操作")
     @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class) })
     @RequestMapping(method = RequestMethod.GET, value = "/forbiddenWords/{id}/{status}")
     public BaseResponse forbiddenWords(@PathVariable @ApiParam(value = "id", required = true) String id,
                                        @PathVariable @ApiParam(value = "status:true:禁言、false：解禁", required = true) Boolean status)
     {
        logger.debug(">> forbiddenWords");
        User user = new User();
        user.setId(id);
        userService.updateNoCache(user);
        return BaseResponse.buildSuccessResponse("设置成功");
     }
}


