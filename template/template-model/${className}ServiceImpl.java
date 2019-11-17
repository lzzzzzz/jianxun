package ${basepackage!""}.${subpackage!""};

import ${basepackage!""}.service.${className!""}Service;

import ${basepackage!""}.entity.${className!""};
import org.openmore.cms.dao.${className!""}Mapper;
import org.openmore.cms.dto.api.${className!""}Dto;

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
public class ${className!""}ServiceImpl extends BaseServiceImpl implements ${className!""}Service{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ${className!""}Mapper ${(className!"")?uncap_first}Mapper;


    @Autowired
    private ReferencedFieldProcessor processor;

    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private ${className!""}Dto convertFrom(${className!""} entity) {
        if (null == entity) {
            return null;
        }
        ${className!""}Dto dto = new ${className!""}Dto();
        BeanUtils.copyProperties(en, dto);
        processor.processOne(dto);
        return dto;
    }
    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private Page<${className!""}Dto> convertFrom(List<${className!""}> entitiesList) {
        Page<${className!""}Dto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (${className!""} en : entitiesList) {
            ${className!""}Dto dto = new ${className!""}Dto();
            BeanUtils.copyProperties(en, dto);
            dtoPageList.add(dto);
        }
//        List<${className!""}Dto> dtoList = dtoPageList.getResult();
        processor.process(dtoPageList);
        return dtoPageList;
    }
    /**
     * 根据id获得实体对象
     * @param id
     */
    @Override
    public ${className!""} getEntityById(String id) {
        Example example = new Example(${className!""}.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
        return ${(className!"")?uncap_first}Mapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     * @param id
     */
    @Override
    public ${className!""}Dto getDtoById(String id) {
        ${className!""}Dto dto = new ${className!""}Dto();
        ${className!""} ${(className!"")?uncap_first} = this.getEntityById(id);
        if (${(className!"")?uncap_first} == null){
            return null;
        }
        BeanUtils.copyProperties(${(className!"")?uncap_first}, dto);
        processor.processOne(dto);
        return dto;
    }

    /**
     * 获得所有记录
     * @return
     */
    @Override
    public List<${className!""}Dto> selectAll() {
        Example example = new Example(${className!""}.class);
        example.createCriteria().andEqualTo("deleted", false);
        List<${className!""}> entityList =  ${(className!"")?uncap_first}Mapper.selectByExample(example);
        insertLog("查询${className!""}列表");
        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(){
        Integer count = 0;
        Example example = new Example(${className!""}.class);
        example.createCriteria().andEqualTo("deleted", false);
        count = ${(className!"")?uncap_first}Mapper.selectCountByExample(example);
        insertLog("查询${className!""}列表数量");
        return null==count?0:count;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     * @param dto
     */
    @Override
    public ${className!""}Dto insert(${className!""}Dto dto){
        if(null==dto){
        throw new OpenmoreException(400, "参数不能为空");
        }
        ${className!""} entity = new ${className!""}();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        ${(className!"")?uncap_first}Mapper.insert(entity);
        insertLog("插入${className!""}项id:"+entity.getId());
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
        ${className!""} entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(${className!""}.class);
            example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
            entity.setDeleted(true);
            ${(className!"")?uncap_first}Mapper.updateByExampleSelective(entity, example);
            insertLog("删除${className!""}项id:"+id);
        }
    }
    /**
     * 修改数据
    */
    @Override
    public void update(${className!""}Dto dto){
        if (null == dto) {
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
        throw new OpenmoreException(400, "id不能为空");
        }
        ${className!""} entity = new ${className!""}();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(${className!""}.class);
        example.createCriteria().andEqualTo("id", entity.getId()).andEqualTo("deleted", false);
        beforeUpdate(entity);
        ${(className!"")?uncap_first}Mapper.updateByExampleSelective(entity, example);
    }
}