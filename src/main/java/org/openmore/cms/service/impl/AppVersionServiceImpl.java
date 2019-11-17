package org.openmore.cms.service.impl;

import org.openmore.cms.entity.enums.AppType;
import org.openmore.cms.service.AppVersionService;

import org.openmore.cms.entity.AppVersion;
import org.openmore.cms.dao.AppVersionMapper;
import org.openmore.cms.dto.api.AppVersionDto;

import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class AppVersionServiceImpl extends BaseServiceImpl implements AppVersionService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AppVersionMapper appVersionMapper;


    @Autowired
    private ReferencedFieldProcessor processor;

    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private Page<AppVersionDto> convertFrom(List<AppVersion> entitiesList) {
        Page<AppVersionDto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (AppVersion en : entitiesList) {
            AppVersionDto dto = new AppVersionDto();
            BeanUtils.copyProperties(en, dto);
            dtoPageList.add(dto);
        }
//        List<AppVersionDto> dtoList = dtoPageList.getResult();
        processor.process(dtoPageList);
        return dtoPageList;
    }
    /**
     * 根据id获得实体对象
     * @param id
     */
    @Override
    public AppVersion getEntityById(String id) {
        Example example = new Example(AppVersion.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
        return appVersionMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     * @param id
     */
    @Override
    public AppVersionDto getDtoById(String id) {
        AppVersionDto dto = new AppVersionDto();
        AppVersion appVersion = this.getEntityById(id);
        if (appVersion == null){
            return null;
        }
        BeanUtils.copyProperties(appVersion, dto);
        processor.processOne(dto);
        return dto;
    }

    /**
     * 获得所有记录
     * @return
     */
    @Override
    public List<AppVersionDto> selectAll(String versionCode, AppType type) {
        Example example = new Example(AppVersion.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(versionCode)){
            criteria.andEqualTo("versionCode", versionCode);
        }
        if(null!=type){
            criteria.andEqualTo("type", type);
        }
        example.orderBy("createdTime").desc();
        List<AppVersion> entityList =  appVersionMapper.selectByExample(example);
        insertLog("查询AppVersion列表");
        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(String versionCode, AppType type){
        Integer count = 0;
        Example example = new Example(AppVersion.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(versionCode)){
            criteria.andEqualTo("versionCode", versionCode);
        }
        if(null!=type){
            criteria.andEqualTo("type", type);
        }
        count = appVersionMapper.selectCountByExample(example);
        insertLog("查询AppVersion列表数量");
        return null==count?0:count;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     * @param dto
     */
    @Override
    public AppVersionDto insert(AppVersionDto dto){
        if(null==dto){
            throw new OpenmoreException(400, "参数不能为空");
        }
        if(null==dto.getType()){
            throw new OpenmoreException(400, " 版本类型不能为空");
        }
        if(StringUtils.isEmpty(dto.getVersionCode())){
            throw new OpenmoreException(400, " 版本号不能为空");
        }
        if(null == dto.getNeedUpdate()){
            dto.setNeedUpdate(false);
        }
        AppVersion entity = new AppVersion();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        appVersionMapper.insert(entity);
        insertLog("插入AppVersion项id:"+entity.getId());
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
        AppVersion entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(AppVersion.class);
            example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
            entity.setDeleted(true);
            appVersionMapper.updateByExampleSelective(entity, example);
            insertLog("删除AppVersion项id:"+id);
        }
    }
    /**
     * 修改数据
    */
    @Override
    public void update(AppVersionDto dto){
        if (null == dto) {
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
        throw new OpenmoreException(400, "id不能为空");
        }
        AppVersion entity = new AppVersion();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(AppVersion.class);
        example.createCriteria().andEqualTo("id", entity.getId()).andEqualTo("deleted", false);
        appVersionMapper.updateByExampleSelective(entity, example);
    }

    /**检查版本更新*/
    public AppVersionDto checkAppVersion(String versionCode, AppType type){
        List<AppVersionDto> oldAppversions = selectAll(versionCode, type);
        if(CollectionUtils.isEmpty(oldAppversions)){
            throw new OpenmoreException(400, "版本号非法");
        }
        AppVersionDto oldVersion = oldAppversions.get(0);
        List<AppVersionDto> newAppversions = selectAll(null, type);
        if(CollectionUtils.isEmpty(newAppversions)){
            throw new OpenmoreException(400, "暂时没有该平台版本");
        }
        AppVersionDto newVersion = newAppversions.get(0);
        if(!oldVersion.getVersionCode().equals(newVersion.getVersionCode())){
            return newVersion;
        }else{
            return null;
        }
    }
}