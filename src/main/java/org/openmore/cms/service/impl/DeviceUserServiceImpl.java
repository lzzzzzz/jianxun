package org.openmore.cms.service.impl;

import org.openmore.cms.service.DeviceUserService;

import org.openmore.cms.entity.DeviceUser;
import org.openmore.cms.dao.DeviceUserMapper;
import org.openmore.cms.dto.api.DeviceUserDto;

import org.apache.commons.collections.CollectionUtils;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import com.github.pagehelper.Page;
import tk.mybatis.mapper.entity.Example;
import org.openmore.common.utils.CommonUtils;
import org.openmore.common.exception.OpenmoreException;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceUserServiceImpl extends BaseServiceImpl implements DeviceUserService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeviceUserMapper deviceUserMapper;


    @Autowired
    private ReferencedFieldProcessor processor;

    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private DeviceUserDto convertFrom(DeviceUser entity) {
        if (null == entity) {
            return null;
        }
        DeviceUserDto dto = new DeviceUserDto();
        BeanUtils.copyProperties(entity, dto);
        processor.processOne(dto);
        return dto;
    }
    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private Page<DeviceUserDto> convertFrom(List<DeviceUser> entitiesList) {
        Page<DeviceUserDto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (DeviceUser en : entitiesList) {
            DeviceUserDto dto = new DeviceUserDto();
            BeanUtils.copyProperties(en, dto);
            dtoPageList.add(dto);
        }
//        List<DeviceUserDto> dtoList = dtoPageList.getResult();
        processor.process(dtoPageList);
        return dtoPageList;
    }
    /**
     * 根据id获得实体对象
     * @param id
     */
    @Override
    public DeviceUser getEntityById(String id) {
        Example example = new Example(DeviceUser.class);
        example.createCriteria().andEqualTo("id", id);
        return deviceUserMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     * @param id
     */
    @Override
    public DeviceUserDto getDtoById(String id) {
        DeviceUserDto dto = new DeviceUserDto();
        DeviceUser deviceUser = this.getEntityById(id);
        if (deviceUser == null){
            return null;
        }
        BeanUtils.copyProperties(deviceUser, dto);
        processor.processOne(dto);
        return dto;
    }
    /**
     * 根据token获得DTO对象
     * @param deviceToken
     */
    @Override
    public DeviceUser getByToken(String deviceToken) {
        if(StringUtils.isEmpty(deviceToken)){
            throw new OpenmoreException(400, "设备号格式非法");
        }
        Example example = new Example(DeviceUser.class);
        example.createCriteria().andEqualTo("deviceToken", deviceToken);
        DeviceUser deviceUser = deviceUserMapper.selectOneByExample(example);
        if(null == deviceUser){
            deviceUser = new DeviceUser();
            DeviceUserDto deviceUserDto = new DeviceUserDto();
            deviceUserDto.setDeviceToken(deviceToken);
            deviceUserDto.setIsVip(false);
            insert(deviceUserDto);
            BeanUtils.copyProperties(deviceUserDto, deviceUser);
        }
        return deviceUser;
    }

    /**
     * 获得所有记录
     * @return
     */
    @Override
    public List<DeviceUserDto> selectAll(String deviceToken) {
        Example example = new Example(DeviceUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(deviceToken)){
            criteria.andEqualTo("deviceToken", deviceToken);
        }
        List<DeviceUser> entityList =  deviceUserMapper.selectByExample(example);
        insertLog("查询DeviceUser列表");
        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(String deviceToken){
        Integer count = 0;
        Example example = new Example(DeviceUser.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(deviceToken)){
            criteria.andEqualTo("deviceToken", deviceToken);
        }
        count = deviceUserMapper.selectCountByExample(example);
        insertLog("查询DeviceUser列表数量");
        return null==count?0:count;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     * @param dto
     */
    @Override
    public DeviceUserDto insert(DeviceUserDto dto){
        if(null==dto){
        throw new OpenmoreException(400, "参数不能为空");
        }
        DeviceUser entity = new DeviceUser();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        deviceUserMapper.insert(entity);
        insertLog("插入DeviceUser项id:"+entity.getId());
        processor.processOne(dto);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 删除指定id的数据
     */
    @Override
    public void deleteById(String id){
        if(StringUtils.isEmpty(id)){
        throw new OpenmoreException(400, "id不能为空");
        }
        DeviceUser entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(DeviceUser.class);
            example.createCriteria().andEqualTo("id", id);
            deviceUserMapper.deleteByExample(example);
            insertLog("删除DeviceUser项id:"+id);
        }
    }
    /**
     * 修改数据
    */
    @Override
    public void update(DeviceUserDto dto){
        if (null == dto) {
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
        throw new OpenmoreException(400, "id不能为空");
        }
        DeviceUser entity = new DeviceUser();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(DeviceUser.class);
        example.createCriteria().andEqualTo("id", entity.getId());
        deviceUserMapper.updateByExampleSelective(entity, example);
    }
}