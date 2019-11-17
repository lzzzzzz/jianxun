package org.openmore.cms.service.impl;

import com.github.pagehelper.Page;
import org.openmore.cms.service.OperateLogService;

import org.openmore.cms.entity.OperateLog;
import org.openmore.cms.dao.OperateLogMapper;
import org.openmore.cms.dto.api.OperateLogDto;

import org.openmore.common.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class OperateLogServiceImpl extends BaseServiceImpl implements OperateLogService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 根据id获得实体对象
     *
     * @param id
     */
    @Override
    public OperateLog getEntityById(String id) {
        Example example = new Example(OperateLog.class);
        example.createCriteria().andEqualTo("id", id);
        return operateLogMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     *
     * @param id
     */
    @Override
    public OperateLogDto getDtoById(String id) {
        OperateLogDto dto = new OperateLogDto();
        OperateLog operateLog = this.getEntityById(id);
        if (operateLog == null) {
            return null;
        }
        BeanUtils.copyProperties(operateLog, dto);
        return dto;
    }

    /**
     * 获得所有记录
     *
     * @return
     */
    @Override
    public List<OperateLogDto> query() {
        return search(null,null,null);
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     *
     * @param dto
     */
    @Override
    @Transactional
    public OperateLogDto insert(OperateLogDto dto) {
        OperateLog entity = new OperateLog();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(CommonUtils.getUUID());
        beforeInsert(entity);
        operateLogMapper.insert(entity);
        dto.setId(entity.getId());
        return dto;
    }

    /**
     * 检索操作日志列表
     *
     * @param staffName:操作员用户名
     * @param startTime:时间左边界
     * @param endTime:时间右边界
     */
    public List<OperateLogDto> search(String staffName, Date startTime, Date endTime) {
        Example example = new Example(OperateLog.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(staffName)) {
            staffName = "%" + staffName + "%";
            criteria.andLike("operator", staffName);
        }
        if (null != startTime) {
            criteria.andGreaterThanOrEqualTo("createdTime", startTime);
        }
        if (null != endTime) {
            criteria.andLessThanOrEqualTo("createdTime", endTime);
        }
        example.orderBy("createdTime").desc();
        List<OperateLog> entityList = operateLogMapper.selectByExample(example);
        Page<OperateLogDto> dtoList = new Page<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return dtoList;
        }
        if (entityList instanceof Page) {
            BeanUtils.copyProperties(entityList, dtoList);
            dtoList.clear();
        }
        for (OperateLog operateLog : entityList) {
            OperateLogDto dto = new OperateLogDto();
            BeanUtils.copyProperties(operateLog, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }
}