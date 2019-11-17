package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.dto.api.AppVersionDto;
import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.entity.ChatMessage;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.LoginType;
import org.openmore.cms.entity.enums.MessageDataType;
import org.openmore.cms.entity.enums.MessageType;
import org.openmore.cms.service.UserService;
import org.openmore.cms.service.impl.ChatService;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.common.shiro.OpenmoreToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(value = "/Account", tags = "Account", description = "账户操作")
@RequestMapping(value = "/api/account", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    /**shiro系统登录注册*/
    @ApiOperation(value = "shiro系统登录注册", response = AppVersionDto.class, responseContainer = "List")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = AppVersionDto.class) })
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = {APPLICATION_JSON_UTF8_VALUE})
    public BaseResponse register(@RequestBody @ApiParam(value = "登录信息")OpenmoreToken loginParams) {
        try {
            if(StringUtils.isEmpty(loginParams.getDeviceToken())){
                return BaseResponse.buildFailResponse(400, "设备识别号错误");
            }
            loginParams.setType(LoginType.SOCKET);
            Subject subject = SecurityUtils.getSubject();
            //已经授权过的重新授权
            if (subject.isAuthenticated()) {
                //没有连接websocket的设备
                List<UserDto> userList = userService.selectAll(null, null, loginParams.getDeviceToken(),
                        null, null,null, true, null, null,
                        null);
                User user = ThreadLocalConfig.getUser();
                User mine = new User();
                mine.setNickName(user.getNickName());
                mine.setHomeNumber(user.getHomeNumber());
                if(CollectionUtils.isEmpty(userList)){
                    return BaseResponse.buildSuccessResponse(mine);
                }
                return BaseResponse.buildSuccessResponse(mine);
            }else {//未登录，执行登录
                subject.login(loginParams);
                User user = ThreadLocalConfig.getUser();
                User mine = new User();
                mine.setNickName(user.getNickName());
                mine.setHomeNumber(user.getHomeNumber());
                return BaseResponse.buildSuccessResponse(mine);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return BaseResponse.buildFailResponse(400, "服务器连接出错");
        }
    }

    /**查询当前加入的房间*/
    @TokenAuthCheck
    @ApiOperation(value = "查询当前加入的房间", response = String.class, responseContainer = "String")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = AppVersionDto.class) })
    @RequestMapping(method = RequestMethod.GET, value = "/joinedHome", consumes = {APPLICATION_JSON_UTF8_VALUE})
    public BaseResponse joinedHome() {

        LOGGER.info("User check home");
        try {
            User user = ThreadLocalConfig.getUser();
            if(null == user || !user.getStatus()){
                return BaseResponse.buildFailResponse(400, "未查询到该在线用户");
            }
            if(StringUtils.isEmpty(user.getJoinNum())){
                List<UserDto> users = userService.selectAll(null, null, null, null,
                        null, user.getHomeNumber(), null, null, null, null);
                if(!CollectionUtils.isEmpty(users)){
                    chatService.sendJoinMessage(user, true);
                    //return BaseResponse.buildFailResponse(1600, "您是群主不能再加入其它房间");
                }
            }else {
                chatService.sendJoinMessage(user, false);
            }
            String homeNumber = StringUtils.isEmpty(user.getJoinNum())? user.getHomeNumber(): user.getJoinNum();
            return BaseResponse.buildSuccessResponse(homeNumber);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return BaseResponse.buildFailResponse(400, "查询当前所在房间出错");
        }
    }
    /**加入聊天房间*/
    @Transactional
    @TokenAuthCheck
    @ApiOperation(value = "加入房间", response = String.class, responseContainer = "String")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = AppVersionDto.class) })
    @RequestMapping(method = RequestMethod.PUT, value = "/joinHome/{homeNumber}", consumes = {APPLICATION_JSON_UTF8_VALUE})
    public BaseResponse joinHome(@PathVariable(value = "homeNumber") @ApiParam(required = true, value = "加入的房间号") String homeNumber) {

        LOGGER.info("User added in home");
        try {
            User userHome = userService.getEntityByHomeNumber(homeNumber);
            if(null == userHome || !userHome.getStatus()){
                return BaseResponse.buildFailResponse(400, "未查询到该在线房间");
            }
            User user = ThreadLocalConfig.getUser();
            if(StringUtils.isEmpty(user.getJoinNum())){
                List<UserDto> users = userService.selectAll(null, null, null, null,
                        null, user.getHomeNumber(), null, null, null, null);
                if(!CollectionUtils.isEmpty(users)){
                    return BaseResponse.buildFailResponse(1600, "您是群主不能再加入其它房间");
                }
            }
            if(!user.getJoinNum().equals(homeNumber)){
                user.setJoinNum(homeNumber);
                userService.update(user);
            }
            chatService.sendJoinMessage(user, false);
            User mineHome = new User();
            mineHome.setNickName(userHome.getNickName());
            mineHome.setHomeNumber(userHome.getHomeNumber());
            return BaseResponse.buildSuccessResponse(mineHome);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
            return BaseResponse.buildFailResponse(400, "加入房间出错");
        }
    }
}