package org.openmore.cms.service;

import org.openmore.cms.dto.api.ForeignResourceDto;
import org.openmore.cms.entity.ForeignResource;
import org.openmore.cms.entity.enums.ResourceForeignType;
import org.springframework.util.StringUtils;
import org.openmore.common.exception.OpenmoreException;
import java.util.List;

public interface ForeignResourceService{

        /**
         * 根据id获得Entity对象
         * @param id
         * @return
         */
        ForeignResource getEntityById(String id);

        /**
         * 根据id获得dto对象
         * @param id
         * @return
         */
        ForeignResourceDto getDtoById(String id);

        /**
         * 分页获得所有记录
         * @return
         */
        List<ForeignResourceDto> selectAll(String resourceId, String foreignId, ResourceForeignType foreignType);

        /**
         * 获得所有记录数量
         *
         * @return
         */
        Integer selectCount(String resourceId, String foreignId, ResourceForeignType foreignType);

        /**
         * 插入指定数据
         * @return
         */
        ForeignResourceDto insert(ForeignResourceDto foreignResource);

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        void deleteById(String fid, String rid);

        /**
         * 更新指定的对象数据
         */
        void update(ForeignResourceDto foreignResource);

        /**批量添加资源关系*/
        void insertResourceList(String foreignId, ResourceForeignType foreignType, String[] resources, String[] params);
        /**清理数据*/
        void clearForeignResources(String foreignId, ResourceForeignType foreignType);
}