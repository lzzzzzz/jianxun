package org.openmore.cms.service.impl;

import com.github.pagehelper.Page;
import org.openmore.cms.dto.MenuDto;
import org.openmore.cms.service.CacheService;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.utils.ChineseToEnglishUtil;
import org.openmore.common.utils.CommonUtils;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.openmore.cms.service.DictionaryService;

import org.openmore.cms.entity.Dictionary;
import org.openmore.cms.dao.DictionaryMapper;
import org.openmore.cms.dto.api.DictionaryDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl implements DictionaryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private ReferencedFieldProcessor processor;

    @Autowired
    private CacheService cacheService;

    /**教材版本存储key*/
    private static final String CACHE_KEY_TEACH_METRIAL="CACHE_DIC_TEACH_METRIALS";
    /**教材科目存储key*/
    private static final String CACHE_KEY_TEACH_OBJECT="CACHE_DIC_TEACH_OBJECTS";
    /**教材年级存储key*/
    private static final String CACHE_KEY_TEACH_GRADE="CACHE_DIC_TEACH_GRADES";
    /**视频艺术类型存储key*/
    private static final String CACHE_KEY_ART_ARTYS="CACHE_DIC_ART_TYPES";

    private static final long CHACHE_TIME = 604800;//7天


    /**
     * 将标签字符串（以逗号间隔）转成标签数组
     *
     * @param commaString
     * @return
     */
    public List<DictionaryDto> processDic(String commaString) {
        if (StringUtils.isEmpty(commaString)) {
            return null;
        }
        List<DictionaryDto> resultList = dictionaryMapper.selectDicsByCommaString(commaString);
        processor.process(resultList);
        return resultList;
    }

    /**
     * 根据id获得实体对象
     *
     * @param id
     */
    @Override
    public Dictionary getEntityById(String id) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("dicKey", id).andEqualTo("deleted", false);
        return dictionaryMapper.selectOneByExample(example);
    }

    /**
     * 根据条件精确匹配字典对象（多条数据抛出异常）
     *
     * @param parentId:父字典key
     * @param name:中文名称
     */
    @Override
    public Dictionary getByName(String parentId, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new OpenmoreException("字典名不能为空");
        }
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if (!StringUtils.isEmpty(parentId)) {
            criteria.andEqualTo("parentKey", parentId);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andEqualTo("name", name);
        }

        return dictionaryMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     *
     * @param id
     */
    @Override
    public DictionaryDto getDtoById(String id) {
        DictionaryDto dto = new DictionaryDto();
        Dictionary dictionary = this.getEntityById(id);
        if (dictionary == null) {
            return null;
        }
        BeanUtils.copyProperties(dictionary, dto);
        processor.processOne(dto);
        return dto;
    }

    /**
     * 搜索菜单
     *
     * @return
     */
    @Override
    public List<DictionaryDto> selectMenu(String type, String name) {
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        Page<DictionaryDto> dtoList = new Page<>();
        if ("first".equals(type)) {
            criteria.andEqualTo("parentKey", "MENU_TYPE");
            if (!StringUtils.isEmpty(name)) {
                criteria.andCondition("(name like '%" + name + "%')");
            }
            example.setOrderByClause("sort_order desc, created_time desc");
            List<Dictionary> entityList = dictionaryMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(entityList)) {
                return dtoList;
            }
            if (entityList instanceof Page) {
                BeanUtils.copyProperties(entityList, dtoList);
                dtoList.clear();
            }
            for (Dictionary dictionary : entityList) {
                DictionaryDto dto = new DictionaryDto();
                BeanUtils.copyProperties(dictionary, dto);
                dtoList.add(dto);
            }
            List<DictionaryDto> resultList = dtoList.getResult();
            processor.process(resultList);
            return dtoList;
        } else if ("second".equals(type)) {
            List<DictionaryDto> resultList = dictionaryMapper.selectSecondMenu(name);
            processor.process(resultList);
            return resultList;
        }
        return dtoList;
    }

    /**
     * 获得所有记录
     *
     * @return
     */
    @Override
    public List<DictionaryDto> selectAll(String type, String name) {
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if (!StringUtils.isEmpty(name)) {
            criteria.andCondition("(name like '%" + name + "%')");
        }
        example.setOrderByClause("parent_key, sort_order desc, created_time desc");
        List<Dictionary> entityList = dictionaryMapper.selectByExample(example);
        Page<DictionaryDto> dtoList = new Page<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return dtoList;
        }
        if (entityList instanceof Page) {
            BeanUtils.copyProperties(entityList, dtoList);
            dtoList.clear();
        }
        for (Dictionary dictionary : entityList) {
            DictionaryDto dto = new DictionaryDto();
            BeanUtils.copyProperties(dictionary, dto);
            dtoList.add(dto);
        }
        List<DictionaryDto> resultList = dtoList.getResult();
        processor.process(resultList);
        return dtoList;
    }

    /**
     * 根据父类id获得所有记录
     *
     * @return
     */
    @Override
    public List<DictionaryDto> selectAll(String parentKey, String typeLevel, String sort, String name) {
        List<Dictionary> entityList;
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if (!StringUtils.isEmpty(sort) && sort.equalsIgnoreCase("asc")) {
            example.setOrderByClause("sort_order asc, created_time desc");
        } else {
            example.setOrderByClause("sort_order desc, created_time desc");
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andCondition("(name like '%" + name + "%')");
        }
        if (!StringUtils.isEmpty(typeLevel)) {
            if (typeLevel.equals("一级")) {
                if (!StringUtils.isEmpty(parentKey)) {
                    criteria.andCondition("parent_key= '" + parentKey + "' ");
                }
                entityList = dictionaryMapper.selectByExample(example);
            } else {
                if (!StringUtils.isEmpty(parentKey)) {
                    criteria.andCondition("parent_key in (select dic_key from dictionary where parent_key='" + parentKey + "' and deleted=0) ");
                }
                entityList = dictionaryMapper.selectByExample(example);
            }
        } else {
            if (!StringUtils.isEmpty(parentKey)) {
                criteria.andCondition("parent_key= '" + parentKey + "' ");
            }
            entityList = dictionaryMapper.selectByExample(example);
        }
        Page<DictionaryDto> dtoList = new Page<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return dtoList;
        }
        if (entityList instanceof Page) {
            BeanUtils.copyProperties(entityList, dtoList);
            dtoList.clear();
        }
        for (Dictionary dictionary : entityList) {
            DictionaryDto dto = new DictionaryDto();
            BeanUtils.copyProperties(dictionary, dto);
            dtoList.add(dto);
        }
        List<DictionaryDto> resultList = dtoList.getResult();
        processor.process(resultList);
        return dtoList;
    }

    /**
     * 词典是否包含指定的key
     *
     * @param key
     * @return
     */
    public boolean existKey(String key) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("deleted", false)
                .andEqualTo("dicKey", key);
        int count = dictionaryMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     *
     * @param dto
     */
    public DictionaryDto insert(DictionaryDto dto) {
        Dictionary entity = new Dictionary();
        BeanUtils.copyProperties(dto, entity);
        if (StringUtils.isEmpty(dto.getDicKey())) {
            // 直接转了 加入随机，怕重复
            entity.setDicKey(ChineseToEnglishUtil.toHanyuPinyin(dto.getName()) + "_" + CommonUtils.randomString(4));
        }
        beforeInsert(entity);
        dictionaryMapper.insert(entity);
        insertLog("创建词典：" + entity.getName());
        BeanUtils.copyProperties(entity, dto);
        processor.processOne(dto);
        return dto;
    }

    /**
     * 保存社区内固定配置信息
     *
     * @param type:类型：bangongdianhua：办公电话 JIN_JI_DIAN_HUA：
     *                                    紧急电话 SHANG_BAN_SHI_JIAN：上班时间 XIA_BAN_SHI_JIAN：下班时间
     *                                    BAN_GONG_SHUO_MING：办公时间说明
     *                                    AN_QUAN_WEI_LAN：安全登录电子围栏
     */
    public DictionaryDto insertConfigDic(String type, String data) {
        if (StringUtils.isEmpty(type)) {
            throw new OpenmoreException("类型不能为空");
        }
//        if (StringUtils.isEmpty(data)) {
//            throw new OpenmoreException("数据不能为空");
//        }
        List<DictionaryDto> dictionarys = getByParent(type, null, false);
        if (!CollectionUtils.isEmpty(dictionarys)) {
            //更新
            dictionarys.get(0).setData(data);
            update(dictionarys.get(0));
            return dictionarys.get(0);
        } else {
            Dictionary dictionary = new Dictionary();
            //新建
            String name = "";
            if ("BAN_GONG_DIAN_HUA".equals(type)) {
                name = "办公电话";
            } else if ("JIN_JI_DIAN_HUA".equals(type)) {
                name = "紧急电话";
            } else if ("SHANG_BAN_SHI_JIAN".equals(type)) {
                name = "上班时间";
            } else if ("XIA_BAN_SHI_JIAN".equals(type)) {
                name = "下班时间";
            } else if ("AN_QUAN_WEI_LAN".equals(type)) {
                name = "安全登录电子围栏";
            } else if ("BAN_GONG_SHUO_MING".equals(type)) {
                name = "办公时间说明";
            } else {
                throw new OpenmoreException("非法参数");
            }
            dictionary.setDicKey(CommonUtils.getUUID());
            dictionary.setParentKey(type);
            dictionary.setData(data);
            dictionary.setName(name);
            beforeInsert(dictionary);
            dictionaryMapper.insert(dictionary);
            DictionaryDto dictionaryDto = new DictionaryDto();
            insertLog("创建词典：" + dictionary.getName());
            BeanUtils.copyProperties(dictionary, dictionaryDto);
            processor.processOne(dictionaryDto);
            return dictionaryDto;
        }
    }

    /**
     * 删除指定id的数据
     */
    public void deleteById(String id) {
        Dictionary entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(Dictionary.class);
            example.createCriteria().andEqualTo("dicKey", id).andEqualTo("deleted", false);
            entity.setDeleted(true);
            dictionaryMapper.updateByExampleSelective(entity, example);
            insertLog("删除词典");
        }
    }

    /**
     * 物理删除指定id的数据
     */
    public void pdeleteById(String id) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("dicKey", id);
        dictionaryMapper.deleteByExample(example);
        insertLog("物理删除词典");
    }


    /**
     * 删除指定父id下的的数据，不包含父id
     */
    public void pdeleteChildrenByParentId(String pid) {
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("parentKey", pid);
        dictionaryMapper.deleteByExample(example);
        insertLog("物理删除父词典下所有子词典：" + pid);
    }

    /**
     * 修改数据
     */
    public void update(DictionaryDto dto) {
        if (null == dto || StringUtils.isEmpty(dto.getDicKey())) {
            throw new OpenmoreException("key不能为空");
        }
        Dictionary entity = new Dictionary();
        BeanUtils.copyProperties(dto, entity);
        if (!StringUtils.isEmpty(dto.getNewDicKey())) {
            entity.setDicKey(dto.getNewDicKey());
        }
        Example example = new Example(Dictionary.class);
        example.createCriteria().andEqualTo("dicKey", dto.getDicKey()).andEqualTo("deleted", false);
        dictionaryMapper.updateByExampleSelective(entity, example);
        insertLog("更新词典：" + entity.getName());
        // 更新processor缓存
        processor.flushCache(Dictionary.class);
    }

    /**
     * 查询二级字典列表
     *
     * @param parentKey :字典父级id
     * @param sort:     排序放式asc:升序，desc:降序 其它：不排序
     */
    @Override
    public List<DictionaryDto> getByParent(String parentKey, String sort, Boolean cache) {
        String sortStr = sort;
        if (StringUtils.isEmpty(sort)) {
            sortStr = "none_sort";
        }
        String key = parentKey + "_" + sortStr;
        if (cache) {
            List<DictionaryDto> resultList = cacheService.get(key, DictionaryDto.class);
            if (resultList != null) {
                return resultList;
            }
        }
        Example example = new Example(Dictionary.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentKey", parentKey).andEqualTo("deleted", false);
        String sortOrderStr = "";
        if (!StringUtils.isEmpty(sort) && "asc".equals(sort)) {
            sortOrderStr = "sort_order asc, created_time desc";
        } else {
            sortOrderStr = "sort_order desc, created_time desc";
        }
        example.setOrderByClause(sortOrderStr);
        List<Dictionary> result = dictionaryMapper.selectByExample(example);
        Page<DictionaryDto> dtoList = new Page<>();
        if (CollectionUtils.isEmpty(result)) {
            return dtoList;
        }
        if (result instanceof Page) {
            BeanUtils.copyProperties(result, dtoList);
            dtoList.clear();
        }
        if (result != null && result.size() > 0) {
            for (Dictionary d : result) {
                DictionaryDto dto = new DictionaryDto();
                BeanUtils.copyProperties(d, dto);
                dtoList.add(dto);
            }
        }
        List<DictionaryDto> resultList = dtoList.getResult();
        processor.process(resultList);
        if (cache) {
            // 默认2小时
            cacheService.set(key, resultList, 7200);
        }
        return dtoList;
    }

    /**
     * 查询父字典
     *
     * @param key:子字典id
     */
    public DictionaryDto getParent(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Dictionary dic = getEntityById(key);
        if (null == dic || StringUtils.isEmpty(dic.getParentKey())) {
            return null;
        }
        DictionaryDto parentDic = getDtoById(dic.getParentKey());
        return parentDic;
    }

    /**
     * 根据父结点，获得所有子节点(父查子)
     *
     * @param parentId
     * @param cache
     * @return
     */
    public List<DictionaryDto> getDicTreeByParentId(String parentId, Boolean cache) {
        String key = "GetDicTreeByParentId_" + parentId;
        if (cache) {
            List<DictionaryDto> resultList = cacheService.get(key, DictionaryDto.class);
            if (resultList != null) {
                return resultList;
            }
        }
        List<DictionaryDto> totalIds = new ArrayList<>();
        List<String> currentIds = new ArrayList<>();
        currentIds.add(parentId);
        while (true) {
            Example example = new Example(Dictionary.class);
            example.createCriteria().andEqualTo("deleted", false).andIn("parentKey", currentIds);
            example.setOrderByClause("sort_order desc, created_time desc");
            List<Dictionary> dicList = dictionaryMapper.selectByExample(example);
            if (dicList == null || dicList.size() == 0) {
                break;
            }
            currentIds = new ArrayList<>();
            for (Dictionary addr : dicList) {
                currentIds.add(addr.getDicKey());
                DictionaryDto dto = new DictionaryDto();
                BeanUtils.copyProperties(addr, dto);
                totalIds.add(dto);
            }
        }
        processor.process(totalIds);
        if (cache) {
            // 默认2小时
            cacheService.set(key, totalIds, 7200);
        }
        return totalIds;
    }

    /**
     * 获得目录树
     *
     * @param parentKey
     * @param cache
     * @return
     */
    public List<MenuDto> getMenuTreeByParentId(String parentKey, Boolean cache) {
        String key = "GetMenuTreeByParentId_" + parentKey;
        if (cache) {
            List<MenuDto> resultList = cacheService.get(key, MenuDto.class);
            if (resultList != null) {
                return resultList;
            }
        }
        List<DictionaryDto> firstLevelMenuList = this.getByParent(parentKey, "desc", cache);
        if (firstLevelMenuList == null || firstLevelMenuList.size() == 0) {
            return null;
        }
//      顶级菜单id
        List<MenuDto> menuList = new ArrayList<>();
        MenuDto menu = null;
//      从二级子菜单里，获得上级菜单列表，并且将二级菜单添加到上级菜单里
        for (DictionaryDto dic : firstLevelMenuList) {
//             顶级菜单，初始化
            menu = new MenuDto();
            BeanUtils.copyProperties(dic, menu);
            menu.children = new ArrayList<>();
            List<DictionaryDto> secondLevelMenuList = this.getByParent(dic.getDicKey(), "desc", cache);
            for (DictionaryDto subDic : secondLevelMenuList) {
                MenuDto subMenu = new MenuDto();
                BeanUtils.copyProperties(subDic, subMenu);
                menu.children.add(subMenu);
            }
            menuList.add(menu);
        }
        if (cache) {
            // 默认2小时
            cacheService.set(key, menuList, 7200);
        }
        return menuList;
    }

    /**获取教材、科目、艺术表现形式分类*/
    public Map<String, List<DictionaryDto>> getTeachInfo(String key, Boolean needRefresh){
        List<DictionaryDto> teachMetrials = null;
        List<DictionaryDto> teachObjects = null;
        List<DictionaryDto> teachGrades = null;
        List<DictionaryDto> artTypes = null;
        Map<String, List<DictionaryDto>> result = new HashMap<>();
        if(null==needRefresh || !needRefresh){
            teachMetrials = cacheService.get(CACHE_KEY_TEACH_METRIAL, DictionaryDto.class);
            teachObjects = cacheService.get(CACHE_KEY_TEACH_OBJECT, DictionaryDto.class);
            teachGrades = cacheService.get(CACHE_KEY_TEACH_GRADE, DictionaryDto.class);
            artTypes = cacheService.get(CACHE_KEY_ART_ARTYS, DictionaryDto.class);
        }
        if(CollectionUtils.isEmpty(teachMetrials)){
            teachMetrials = this.getByParent("TEACH_MATERIAL", null, false);
            cacheService.set(CACHE_KEY_TEACH_METRIAL, teachMetrials, CHACHE_TIME);
        }
        if(CollectionUtils.isEmpty(teachGrades)){
            teachGrades = this.getByParent("TEACH_GRADE", "asc", false);
            cacheService.set(CACHE_KEY_TEACH_GRADE, teachGrades, CHACHE_TIME);
        }
        if(CollectionUtils.isEmpty(teachObjects)){
            teachObjects = this.getByParent("TEACH_SUBJECT", null, false);
            cacheService.set(CACHE_KEY_TEACH_OBJECT, teachObjects, CHACHE_TIME);
        }
        if(CollectionUtils.isEmpty(artTypes)){
            artTypes = this.getByParent("TEACH_ART_TYPES", null, false);
            cacheService.set(CACHE_KEY_TEACH_OBJECT, artTypes, CHACHE_TIME);
        }
        /**单独获取某个类型*/
        if(!StringUtils.isEmpty(key)){
            if(key.equals("materials")){
                result.put("materials", teachMetrials);
                return result;
            }else if(key.equals("objects")){
                result.put("objects", teachObjects);
                return result;
            }else if(key.equals("artTypes")){
                result.put("artTypes", artTypes);
                return result;
            }else if(key.equals("grades")){
                result.put("grades", teachGrades);
                return result;
            }
        }
        result.put("materials", teachMetrials);
        result.put("objects", teachObjects);
        result.put("grades", teachGrades);
        result.put("artTypes", artTypes);
        return result;
    }
}
