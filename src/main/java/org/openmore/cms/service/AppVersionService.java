package org.openmore.cms.service;

import org.openmore.cms.dto.api.AppVersionDto;
import org.openmore.cms.entity.AppVersion;
import org.openmore.cms.entity.enums.AppType;

import java.util.List;

public interface AppVersionService{

        /**
         * 根据id获得Entity对象
         * @param id
         * @return
         */
        AppVersion getEntityById(String id);

        /**
         * 根据id获得dto对象
         * @param id
         * @return
         */
        AppVersionDto getDtoById(String id);

        /**
         * 分页获得所有记录
         * @return
         */
        List<AppVersionDto> selectAll(String versionCode, AppType type);

        /**
         * 获得所有记录数量
         *
         * @return
         */
        Integer selectCount(String versionCode, AppType type);

        /**
         * 插入指定数据
         * @return
         */
        AppVersionDto insert(AppVersionDto appVersion);

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        /**
         * 更新指定的对象数据
         */
        void update(AppVersionDto appVersion);

        /**检查版本更新*/
        AppVersionDto checkAppVersion(String versionCode, AppType type);
        }