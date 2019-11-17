package org.openmore.cms.service.impl;

import org.openmore.cms.controller.common.ThreadLocalConfig;
import org.openmore.cms.entity.User;
import org.openmore.cms.service.FeedbackService;

import org.openmore.cms.entity.Feedback;
import org.openmore.cms.dao.FeedbackMapper;
import org.openmore.cms.dto.api.FeedbackDto;

import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

@Service
public class FeedbackServiceImpl extends BaseServiceImpl implements FeedbackService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FeedbackMapper feedbackMapper;


    @Autowired
    private ReferencedFieldProcessor processor;

    /**
     * 将实体list转成带有分页的PageList
     * @return
     */
    private Page<FeedbackDto> convertFrom(List<Feedback> entitiesList) {
        Page<FeedbackDto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (Feedback en : entitiesList) {
            FeedbackDto dto = new FeedbackDto();
            BeanUtils.copyProperties(en, dto);
            dtoPageList.add(dto);
        }
//        List<FeedbackDto> dtoList = dtoPageList.getResult();
        processor.process(dtoPageList);
        return dtoPageList;
    }
    /**
     * 根据id获得实体对象
     * @param id
     */
    @Override
    public Feedback getEntityById(String id) {
        Example example = new Example(Feedback.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
        return feedbackMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     * @param id
     */
    @Override
    public FeedbackDto getDtoById(String id) {
        FeedbackDto dto = new FeedbackDto();
        Feedback feedback = this.getEntityById(id);
        if (feedback == null){
            return null;
        }
        BeanUtils.copyProperties(feedback, dto);
        processor.processOne(dto);
        return dto;
    }

    /**
     * 获得所有记录
     * @return
     */
    @Override
    public List<FeedbackDto> selectAll(String userId, String content, String contact) {
        Example example = new Example(Feedback.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(userId)){
            criteria.andEqualTo("userId", userId);
        }
        if(!StringUtils.isEmpty(content)){
            content = "%" + content + "%";
            criteria.andLike("content", content);
        }
        if(!StringUtils.isEmpty(contact)){
            contact = "%" + contact + "%";
            criteria.andLike("contact", contact);
        }
        example.orderBy("createdTime").desc();
        List<Feedback> entityList =  feedbackMapper.selectByExample(example);
        insertLog("查询Feedback列表");
        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(String userId, String content, String contact){
        Integer count = 0;
        Example example = new Example(Feedback.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(userId)){
            criteria.andEqualTo("userId", userId);
        }
        if(!StringUtils.isEmpty(content)){
            content = "%" + content + "%";
            criteria.andLike("content", content);
        }
        if(!StringUtils.isEmpty(contact)){
            contact = "%" + contact + "%";
            criteria.andLike("contact", contact);
        }
        count = feedbackMapper.selectCountByExample(example);
        insertLog("查询Feedback列表数量");
        return null==count?0:count;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     * @param dto
     */
    @Override
    public FeedbackDto insert(FeedbackDto dto){
        if(null==dto){
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getContent())){
            throw new OpenmoreException(400, "反馈内容不能为空");
        }
        Feedback entity = new Feedback();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        feedbackMapper.insert(entity);
        insertLog("插入Feedback项id:"+entity.getId());
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
        Feedback entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(Feedback.class);
            example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
            entity.setDeleted(true);
            feedbackMapper.updateByExampleSelective(entity, example);
            insertLog("删除Feedback项id:"+id);
        }
    }
    /**
     * 修改数据
    */
    @Override
    public void update(FeedbackDto dto){
        if (null == dto) {
        throw new OpenmoreException(400, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
        throw new OpenmoreException(400, "id不能为空");
        }
        Feedback entity = new Feedback();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(Feedback.class);
        example.createCriteria().andEqualTo("id", entity.getId()).andEqualTo("deleted", false);
        feedbackMapper.updateByExampleSelective(entity, example);
    }
}