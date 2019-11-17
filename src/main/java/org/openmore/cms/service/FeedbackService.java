package org.openmore.cms.service;

import org.openmore.cms.dto.api.FeedbackDto;
import org.openmore.cms.entity.Feedback;
import org.springframework.util.StringUtils;
import org.openmore.common.exception.OpenmoreException;
import java.util.List;

public interface FeedbackService{

        /**
         * 根据id获得Entity对象
         * @param id
         * @return
         */
        Feedback getEntityById(String id);

        /**
         * 根据id获得dto对象
         * @param id
         * @return
         */
        FeedbackDto getDtoById(String id);

        /**
         * 分页获得所有记录
         * @param userId:用户id
         * @param content:评论内容
         * @return
         */
        List<FeedbackDto> selectAll(String userId, String content, String contact);

        /**
         * 获得所有记录数量
         *
         * @return
         */
        Integer selectCount(String userId, String content, String contact);

        /**
         * 插入指定数据
         * @return
         */
        FeedbackDto insert(FeedbackDto feedback);

        /**
         * 根据id删除数据
         */
        void deleteById(String id);

        /**
         * 更新指定的对象数据
         */
        void update(FeedbackDto feedback);
        }