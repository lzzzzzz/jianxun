package org.openmore.cms.dao;

import org.apache.ibatis.annotations.Param;
import org.openmore.cms.dto.api.UserDto;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.Platform;
import org.openmore.cms.entity.enums.UserType;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface UserMapper extends Mapper<User> {
    /**
     * 根据权限code，查找拥有权限的员工列表
     *
     * @return
     */
    List<UserDto> selectHasPermissionUser(@Param("code") String code);

    /**
     * 查找
     */
    List<User> selectUsers(@Param("id") String id, @Param("username") String username, @Param("type") UserType type,
                           @Param("roleId") String roleId, @Param("isVip")Boolean isVip, @Param("phone") String phone,
                           @Param("platformName") String platformName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Long selectUserCount(@Param("id") String id, @Param("username") String username, @Param("type") UserType type,
                         @Param("roleId") String roleId, @Param("isVip")Boolean isVip, @Param("phone") String phone,
                         @Param("platformName") String platformName, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}