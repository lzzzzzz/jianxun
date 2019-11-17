package org.openmore.cms.service;

import org.openmore.cms.dto.api.OperateLogDto;
import org.openmore.cms.entity.OperateLog;

import java.util.Date;
import java.util.List;

public interface OperateLogService {
    /**
    * 根据id获得Entity对象
    * @param id
    * @return
    */
    OperateLog getEntityById(String id);

    /**
     * 根据id获得dto对象
     * @param id
     * @return
     */
    OperateLogDto getDtoById(String id);

    /**
     * 分页获得所有记录
     * @return
     */
    List<OperateLogDto> query();

     /**
      * 插入指定数据
      * @return
     */
    OperateLogDto insert(OperateLogDto operateLog);

    /**检索操作日志列表
     *
     * @param staffName:工作人员姓名
     * @param startTime:时间左边界
     * @param endTime:时间右边界
     */
    List<OperateLogDto> search(String staffName, Date startTime, Date endTime);
}