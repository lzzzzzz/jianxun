package org.openmore.cms.service.impl;

import org.openmore.cms.dto.api.ResourcesDto;
import org.openmore.cms.entity.enums.ResourceForeignType;
import org.openmore.cms.service.ForeignResourceService;

import org.openmore.cms.entity.ForeignResource;
import org.openmore.cms.dao.ForeignResourceMapper;
import org.openmore.cms.dto.api.ForeignResourceDto;

import org.openmore.cms.service.ResourcesService;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import com.github.pagehelper.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import org.openmore.common.utils.CommonUtils;
import org.openmore.common.exception.OpenmoreException;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

@Service
public class ForeignResourceServiceImpl extends BaseServiceImpl implements ForeignResourceService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ForeignResourceMapper foreignResourceMapper;

    @Autowired
    private ResourcesService resourcesService;


    @Autowired
    private ReferencedFieldProcessor processor;

    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private ForeignResourceDto convertFrom(ForeignResource entity) {
            ForeignResourceDto dto = new ForeignResourceDto();
            BeanUtils.copyProperties(entity, dto);
//        List<ForeignResourceDto> dtoList = dtoPageList.getResult();
        processor.processOne(dto);
        return dto;
    }
    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private Page<ForeignResourceDto> convertFrom(List<ForeignResource> entitiesList) {
        Page<ForeignResourceDto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (ForeignResource en : entitiesList) {
            ForeignResourceDto dto = new ForeignResourceDto();
            BeanUtils.copyProperties(en, dto);
            dtoPageList.add(dto);
        }
//        List<ForeignResourceDto> dtoList = dtoPageList.getResult();
        processor.process(dtoPageList);
        return dtoPageList;
    }
    /**
     * 根据id获得实体对象
     * @param id
     */
    @Override
    public ForeignResource getEntityById(String id) {
        Example example = new Example(ForeignResource.class);
        example.createCriteria().andEqualTo("id", id);
        return foreignResourceMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     * @param id
     */
    @Override
    public ForeignResourceDto getDtoById(String id) {
        ForeignResource foreignResource = this.getEntityById(id);
        if (foreignResource == null){
            return null;
        }
        return convertFrom(foreignResource);
    }

    /**
     * 获得所有记录
     * @return
     */
    @Override
    public List<ForeignResourceDto> selectAll(String resourceId, String foreignId, ResourceForeignType foreignType) {
        Example example = new Example(ForeignResource.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(resourceId)){
            criteria.andEqualTo("resourceId", resourceId);
        }
        if(!StringUtils.isEmpty(foreignId)){
            criteria.andEqualTo("foreignId", foreignId);
        }
        if(null!=foreignType){
            criteria.andEqualTo("foreignType", foreignType);
        }
        example.orderBy("createdTime").desc();
        List<ForeignResource> entityList =  foreignResourceMapper.selectByExample(example);
        insertLog("查询ForeignResource列表");
        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(String resourceId, String foreignId, ResourceForeignType foreignType){
        Integer count = 0;
        Example example = new Example(ForeignResource.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(resourceId)){
            criteria.andEqualTo("resourceId", resourceId);
        }
        if(!StringUtils.isEmpty(foreignId)){
            criteria.andEqualTo("foreignId", foreignId);
        }
        if(null!=foreignType){
            criteria.andEqualTo("foreignType", foreignType);
        }
        count = foreignResourceMapper.selectCountByExample(example);
        insertLog("查询ForeignResource列表数量");
        return null==count?0:count;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     * @param dto
     */
    @Override
    public ForeignResourceDto insert(ForeignResourceDto dto){
        if(null==dto){
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getResourceId())){
            throw new OpenmoreException("资源id不能为空");
        }
        if(StringUtils.isEmpty(dto.getForeignId())){
            throw new OpenmoreException("关联id不能为空");
        }
        ForeignResource entity = new ForeignResource();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        foreignResourceMapper.insert(entity);
        insertLog("插入ForeignResource项id:"+entity.getId());
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
        ForeignResource entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(ForeignResource.class);
            example.createCriteria().andEqualTo("id", id);
            foreignResourceMapper.deleteByExample(example);
            insertLog("删除ForeignResource项id:"+id);
        }
    }


    /**
     * 删除指定id的数据
     */
    @Override
    public void deleteById(String fid, String rid){
        Example example = new Example(ForeignResource.class);
        example.createCriteria().andEqualTo("foreignId", fid);
        example.createCriteria().andEqualTo("resourceId", rid);
        foreignResourceMapper.deleteByExample(example);
    }


    /**
     * 修改数据
    */
    @Override
    public void update(ForeignResourceDto dto){
        if (null == dto) {
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
        throw new OpenmoreException(400, "id不能为空");
        }
        ForeignResource entity = new ForeignResource();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(ForeignResource.class);
        example.createCriteria().andEqualTo("id", entity.getId());
        foreignResourceMapper.updateByExampleSelective(entity, example);
    }


    /**清理数据*/
    @Override
    public void clearForeignResources(String foreignId, ResourceForeignType foreignType){
        if(StringUtils.isEmpty(foreignId) || null == foreignType){
            throw new OpenmoreException(400, "参数不能为空");
        }
        Example example = new Example(ForeignResource.class);
        example.createCriteria().andEqualTo("foreignId", foreignId).andEqualTo("foreignType", foreignType);
        foreignResourceMapper.deleteByExample(example);

    }
    /**
     * 将逗号间隔的资源内容插入
     *
     * @param foreignId
     * @param foreignType
     * @param resources
     * @return
     */
    @Transactional
    @Override
    public void insertResourceList(String foreignId, ResourceForeignType foreignType, String[] resources, String[] params) {
        if(StringUtils.isEmpty(foreignId)){
            throw new OpenmoreException("关联id不能为空");
        }
        if(null == resources || resources.length<=0){
            throw new OpenmoreException("资源id不能为空");
        }
        if (resources.length > 6) {
            throw new OpenmoreException("最多可以添加6个资源");
        }
        if (params != null && resources.length != params.length) {
            throw new OpenmoreException("资源与对应参数不匹配");
        }
        if(null == foreignType){
            foreignType = ResourceForeignType.OTHERS;
        }
        List<ForeignResource> foreignResources = new ArrayList<>();
        int i = 0;
        for (String res : resources) {
            List<ForeignResourceDto> articleResourceDtos = selectAll(res, foreignId, foreignType);
            if (!CollectionUtils.isEmpty(articleResourceDtos)) {
                i++;
                continue;
            }
            ForeignResource arEntity = new ForeignResource();
            arEntity.setForeignId(foreignId);
            arEntity.setForeignType(foreignType);
            arEntity.setResourceId(res);
            beforeInsert(arEntity);
            // 区分开时间，来排序图片
            arEntity.setCreatedTime(new Date(System.currentTimeMillis() + i * 5));
            foreignResources.add(arEntity);
            i++;
        }
        for(ForeignResource foreignResource: foreignResources){
            foreignResourceMapper.insert(foreignResource);
            insertLog(" 添加资源关系id:" + foreignResource.getId());
        }
    }
}