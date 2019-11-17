package org.openmore.cms.service;

import org.openmore.cms.dto.MenuDto;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.cms.entity.Dictionary;
import java.util.List;
import java.util.Map;

public interface DictionaryService {
    /**
     * 将标签字符串（以逗号间隔）转成标签数组
     * @param commaString
     * @return
     */
    List<DictionaryDto> processDic(String commaString);
    /**
    * 根据id获得Entity对象
    * @param id
    * @return
    */
    Dictionary getEntityById(String id);

    /**
     * 根据id获得dto对象
     * @param id
     * @return
     */
    DictionaryDto getDtoById(String id);

    /**
     * 分页获得所有记录
     * @return
     */
    List<DictionaryDto> selectAll(String type, String name);

    /**
     * 根据父类id分页获得所有记录
     * @return
     */
    List<DictionaryDto> selectAll(String parentKey, String typeLevel, String sort, String name);

    /**
     * 后台菜单管理分页获得菜单数据
     * @return
     */
    List<DictionaryDto> selectMenu(String type, String name);

     /**
      * 插入指定数据
      * @return
     */
    DictionaryDto insert(DictionaryDto dictionary);

    /**
     * 保存社区内固定配置信息
     *
     * @param type:类型：bangongdianhua：办公电话 JIN_JI_DIAN_HUA：
     *                                    紧急电话 SHANG_BAN_SHI_JIAN：上班时间 XIA_BAN_SHI_JIAN：下班时间
     *                                    BAN_GONG_SHUO_MING：办公时间说明
     */
    DictionaryDto insertConfigDic(String type, String data);

    /**
     * 根据id删除数据
     */
    void deleteById(String id);

    /**
     * 更新指定的对象数据
     */
    void update(DictionaryDto dictionary);

    /**
     * 查询二级字典列表
     */
    List<DictionaryDto> getByParent(String parentKey, String sort, Boolean cache);

    /**
     * 根据条件精确匹配字典对象（多条数据抛出异常）
     *
     * @param parentId:父字典key
     * @param name:中文名称
     */
    Dictionary getByName(String parentId, String name);

    /**
     * 查询父字典
     *
     * @param key:子字典id
     */
    DictionaryDto getParent(String key);


    /**
     * 根据父结点，获得所有子节点
     * @param parentId
     * @return
     */
    List<DictionaryDto> getDicTreeByParentId(String parentId, Boolean cache);


    /**
     * 词典是否包含指定的key
     * @param key
     * @return
     */
    boolean existKey(String key);

    /**
     * 物理删除指定id的数据
     */
    void pdeleteById( String id);
    /**
     * 删除指定父id下的的数据，不包含父id
     */
    void pdeleteChildrenByParentId(String pid);

    /**
     * 获得目录树
     * @param parentKey
     * @param cache
     * @return
     */
    List<MenuDto> getMenuTreeByParentId(String parentKey, Boolean cache);

    /**获取教材、科目、艺术表现形式分类*/
    Map<String, List<DictionaryDto>> getTeachInfo(String key, Boolean needRefresh);
}
