package org.openmore.cms.service;

import org.openmore.cms.dto.api.ResourcesDto;
import org.openmore.cms.entity.Resources;
import org.openmore.cms.entity.enums.ResourceType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ResourcesService {

    /**
     * 根据id获得Entity对象
     *
     * @param id
     * @return
     */
    Resources getEntityById(String id);

    /**
     * 根据id获得dto对象
     *
     * @param id
     * @return
     */
    ResourcesDto getDtoById(String id);

    /**
     * 分页获得所有记录
     *
     * @return
     */
    List<ResourcesDto> selectAll(String name, String key, ResourceType fileType, Date startTime, Date endTime);

    /**
     * 获得所有记录数量
     *
     * @return
     */
    Integer selectCount(String name, String key, ResourceType fileType, Date startTime, Date endTime);

    /**
     * 插入指定数据
     *
     * @return
     */
    ResourcesDto insert(ResourcesDto resources);

    /**
     * 批量保存多个文件
     */
    List<ResourcesDto> insertFiles(MultipartFile[] files);

    /**
     * 保存单个文件
     */
    ResourcesDto insertFile(MultipartFile file, String fileSize);

    /**
     * 根据id删除数据
     */
    void deleteById(String id);

    /**
     * 更新指定的对象数据
     */
    void update(ResourcesDto resources);

    /**
     * 获得直传Key
     * @return
     */
    Map<String, String> directUpload();


    ResourceType getResourceType(String fileName);

    List<ResourcesDto> selectResourcesByForeignId(String foreignId);

    /**读取excel保利vid视频资源
     * 格式：
     * 视频名称 | 视频vid
     * 小露珠  |  123
     * */
    String parsePolvVideo(MultipartFile file);

    /**创建保利上传模版*/
    void createPolvExcelModel(HttpServletResponse response);
}