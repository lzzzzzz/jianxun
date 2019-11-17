package org.openmore.cms.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.openmore.cms.dao.UserMapper;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.LoginType;
import org.openmore.cms.service.*;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.shiro.OpenmoreToken;
import org.openmore.common.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReferencedFieldProcessor processor;


    private UserDto convert(User user) {
        if (null == user) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        processor.processOne(userDto);
        return userDto;
    }

    private List<UserDto> convert(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            List<UserDto> userDto = new ArrayList<>();
            return userDto;
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            processor.processOne(userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    /**
     * 根据id获得实体对象
     *
     * @param id
     */
    @Override
    public User getEntityById(String id) {
        if(StringUtils.isEmpty(id)){
            return null;
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", id);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User getEntityByHomeNumber(String homeNumber) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("homeNumber", homeNumber);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public User getEntityByDeviceToken(String deviceToken) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("deviceToken", deviceToken);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     *
     * @param id
     */
    @Override
    public UserDto getDtoById(String id) {
        return convert(getEntityById(id));
    }

    /**
     * 获得所有记录
     *
     * @return
     */
    @Override
    public List<UserDto> selectAll(String id, String homeNumber, String deviceToken, String ipAddress, String macAddress,
                                   String joinNum, Boolean status, Boolean locked, Date startTime, Date endTime) {

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andEqualTo("id", id);
        }
        if(!StringUtils.isEmpty(homeNumber)){
            criteria.andEqualTo("homeNumber", homeNumber);
        }
        if(!StringUtils.isEmpty(deviceToken)){
            criteria.andEqualTo("deviceToken", deviceToken);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            criteria.andEqualTo("ipAddress", ipAddress);
        }
        if(!StringUtils.isEmpty(macAddress)){
            criteria.andEqualTo("macAddress", macAddress);
        }
        if(!StringUtils.isEmpty(macAddress)){
            criteria.andEqualTo("macAddress", macAddress);
        }
        if(!StringUtils.isEmpty(joinNum)){
            criteria.andEqualTo("joinNum", joinNum);
        }
        if(null != status){
            criteria.andEqualTo("status", status);
        }
        if(null != locked){
            criteria.andEqualTo("locked", locked);
        }
        if(null!=startTime && null!= endTime && endTime.before(startTime)){
            endTime=startTime;
        }
        if(null!=startTime){
            criteria.andGreaterThanOrEqualTo("createdTime", startTime);
        }
        if(null!=endTime){
            criteria.andLessThanOrEqualTo("createdTime", startTime);
        }
        example.orderBy("createdTime").desc();
        List<User> users = userMapper.selectByExample(example);
        return convert(users);
    }

    /**
     * 获得所有数量
     *
     * @return
     */
    @Override
    public long selectAllCount(String id, String homeNumber, String deviceToken, String ipAddress, String macAddress,
                               String joinNum, Boolean status, Boolean locked, Date startTime, Date endTime) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(id)){
            criteria.andEqualTo("id", id);
        }
        if(!StringUtils.isEmpty(homeNumber)){
            criteria.andEqualTo("homeNumber", homeNumber);
        }
        if(!StringUtils.isEmpty(deviceToken)){
            criteria.andEqualTo("deviceToken", deviceToken);
        }
        if(!StringUtils.isEmpty(ipAddress)){
            criteria.andEqualTo("ipAddress", ipAddress);
        }
        if(!StringUtils.isEmpty(macAddress)){
            criteria.andEqualTo("macAddress", macAddress);
        }
        if(!StringUtils.isEmpty(macAddress)){
            criteria.andEqualTo("macAddress", macAddress);
        }
        if(!StringUtils.isEmpty(joinNum)){
            criteria.andEqualTo("joinNum", joinNum);
        }
        if(null != status){
            criteria.andEqualTo("status", status);
        }
        if(null != locked){
            criteria.andEqualTo("locked", locked);
        }
        if(null!=startTime && null!= endTime && endTime.before(startTime)){
            endTime=startTime;
        }
        if(null!=startTime){
            criteria.andGreaterThanOrEqualTo("createdTime", startTime);
        }
        if(null!=endTime){
            criteria.andLessThanOrEqualTo("createdTime", startTime);
        }
        Integer count = userMapper.selectCountByExample(example);
        return null == count ? 0 : count;
    }


    public UserDto insert(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return this.insert(entity);
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     *
     * @param entity
     */
    @Override
    @Transactional
    public UserDto insert(User entity) {
        // 密码不能为空
        // 三方登录信息不能全为空
        // 手机号和邮件不能全为空
        /*if (StringUtils.isEmpty(entity.getEmail()) && StringUtils.isBlank(entity.getPhone()) ||
                (StringUtils.isBlank(entity.getQqOpenid()) && StringUtils.isBlank(entity.getWechatOpenid()) && StringUtils.isBlank(entity.getWeiboOpenid()))) {
            throw new InvalidParamsException("缺少必须的参数，请检查三方登录信息/邮箱/手机号/密码");
        }*/
        if(StringUtils.isEmpty(entity.getDeviceToken())){
            throw new OpenmoreException(400, "设备识别号错误");
        }
        entity.setId(CommonUtils.getUUID());
        entity.setStatus(false);
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
        entity.setLocked(false);
        beforeInsert(entity);

        userMapper.insert(entity);
        insertLog("添加用户deviceToken：" + entity.getDeviceToken());

        return this.getDtoById(entity.getId());
    }

    /**
     * 删除指定id的数据
     */
    @Override
    public void deleteById(String id) {
        User entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(User.class);
            example.createCriteria().andEqualTo("id", id);
            userMapper.updateByExampleSelective(entity, example);
            insertLog("delete User");
        }
    }

    @Override
    public void update(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        this.update(entity);
    }

    /**
     * 修改数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            throw new OpenmoreException("id不能为空");
        }
        User old = getEntityById(entity.getId());
        if (null == old) {
            throw new OpenmoreException("id非法");
        }
        Example example = new Example(User.class);

        example = new Example(User.class);
        example.createCriteria().andEqualTo("id", entity.getId());
        userMapper.updateByExampleSelective(entity, example);
        insertLog("更新用户信息deviceToken：" + entity.getDeviceToken());
        User User = getEntityById(entity.getId());
//      更新用户信息后，需要更新session里的用户信息
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(subject.getPrincipal(), JSON.toJSONString(User));
        // 清除process缓存
        processor.flushCache(User.class);
    }

    /**不需要更新缓存的修改*/
    @Override
    @Transactional
    public void updateNoCache(User entity){
        if (StringUtils.isEmpty(entity.getId())) {
            throw new OpenmoreException("id不能为空");
        }
        User old = getEntityById(entity.getId());
        if (null == old) {
            throw new OpenmoreException("id非法");
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id", entity.getId());
        userMapper.updateByExampleSelective(entity, example);
        insertLog("更新用户信息deviceToken：" + entity.getDeviceToken());
    }

    @Override
    public void login(OpenmoreToken openmoreToken) {

    }

    /**
     * 返回拥有指定权限的员工列表
     *
     * @param permissionCode
     * @return
     */
    public List<UserDto> selectHasPermissionUserList(String permissionCode) {
        return userMapper.selectHasPermissionUser(permissionCode);
    }
}
