package org.openmore.cms.dao;

import org.openmore.cms.entity.OperateLog;
import tk.mybatis.mapper.common.Mapper;

public interface OperateLogMapper extends Mapper<OperateLog> {

   /* List<OperateLog> search(@Param("tenantId") String tenantId, @Param("staffName") String staffName,
                            @Param("residentName") String residentName,
                            @Param("startTime") Date startTime, @Param("endTime") Date endTime);*/
}