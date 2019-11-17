package ${basepackage!""}.${subpackage!""};

import org.openmore.cms.dto.api.${className!""}Dto;
import org.openmore.cms.entity.${className!""};
import org.springframework.util.StringUtils;
import org.openmore.common.exception.OpenmoreException;
import java.util.List;

public interface ${className!""}Service{

        /**
         * 根据id获得Entity对象
         * @param id
         * @return
         */
        ${className!""} getEntityById(String id);

        /**
         * 根据id获得dto对象
         * @param id
         * @return
         */
        ${className!""}Dto getDtoById(String id);

        /**
         * 分页获得所有记录
         * @return
         */
        List<${className!""}Dto> selectAll();

        /**
         * 获得所有记录数量
         *
         * @return
         */
        Integer selectCount();

        /**
         * 插入指定数据
         * @return
         */
        ${className!""}Dto insert(${className!""}Dto ${(className!"")?uncap_first});

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        /**
         * 更新指定的对象数据
         */
        void update(${className!""}Dto ${(className!"")?uncap_first});
        }