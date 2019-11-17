package org.openmore.cms.service;

import org.openmore.cms.dto.api.DeviceUserDto;
import org.openmore.cms.entity.DeviceUser;
import org.springframework.util.StringUtils;
import org.openmore.common.exception.OpenmoreException;
import java.util.List;

public interface DeviceUserService{

        /**
         * 根据id获得Entity对象
         * @param id
         * @return
         */
        DeviceUser getEntityById(String id);

        /**
         * 根据id获得dto对象
         * @param id
         * @return
         */
        DeviceUserDto getDtoById(String id);
        /**
         * 根据token获得DTO对象
         * @param deviceToken
         */
        public DeviceUser getByToken(String deviceToken);

        /**
         * 分页获得所有记录
         * @return
         */
        List<DeviceUserDto> selectAll(String deviceToken);

        /**
         * 获得所有记录数量
         *
         * @return
         */
        Integer selectCount(String deviceToken);

        /**
         * 插入指定数据
         * @return
         */
        DeviceUserDto insert(DeviceUserDto deviceUser);

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        /**
         * 更新指定的对象数据
         */
        void update(DeviceUserDto deviceUser);
        }