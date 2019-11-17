package org.openmore.cms.service;

import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.entity.User;
import org.openmore.common.shiro.OpenmoreToken;

import java.util.Date;
import java.util.List;

public interface UserService {

        /**
         * 根据id获得Entity对象
         *
         * @param id
         * @return
         */
        User getEntityById(String id);
        /**
         * 根据number获得Entity对象
         *
         * @param number
         * @return
         */
        User getEntityByHomeNumber(String number);
        /**
         * 根据number获得Entity对象
         *
         * @param deviceToken
         * @return
         */
        User getEntityByDeviceToken(String deviceToken);

        /**
         * 根据id获得dto对象
         *
         * @param id
         * @return
         */
        UserDto getDtoById(String id);

        /**
         * 分页获得所有记录
         *
         * @return
         */
        List<UserDto> selectAll(String id, String number, String deviceToken, String ipAddress, String macAddress,
                                String joinNum, Boolean status, Boolean locked, Date startTime, Date endTime);

        /**
         * 获得所有记录
         */
        long selectAllCount(String id, String number, String deviceToken, String ipAddress, String macAddress,
                            String joinNum, Boolean status, Boolean locked, Date startTime, Date endTime);

        /**
         * 插入指定数据
         *
         * @return
         */
        UserDto insert(User user);
        /**
         * 插入指定数据
         *
         * @return
         */
        UserDto insert(UserDto dto);

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        /**
         * 更新指定的对象数据
         */
        void update(UserDto userDto);

        /**
         * 更新指定的对象数据
         */
        void update(User user);

        /**不需要更新缓存的修改*/
        void updateNoCache(User entity);

        public void login(OpenmoreToken openmoreToken);
}