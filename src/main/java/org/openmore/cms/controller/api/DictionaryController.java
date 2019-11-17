package org.openmore.cms.controller.api;

import io.swagger.annotations.*;
import org.openmore.cms.service.CacheService;
import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.dto.StringParams;
import org.openmore.cms.entity.Dictionary;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import org.openmore.cms.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.common.exception.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore
 * on 2018-08-11
 */
@Api(value = "/Dictionary", tags = "Dictionary", description = "词典")
@RequestMapping(value = "/api/dictionary", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class DictionaryController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final String KEY_MENUS = "index_menus";

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation(value = "分页返回所有菜单数据", response = DictionaryDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的Activity", response = DictionaryDto.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/queryMenu")
    public BaseResponse selectMenuByPage(
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "菜单类型：first一级菜单，second二级菜单") String type,
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "菜单名") String name,
            @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
            @RequestParam(required = false, defaultValue = "50") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> selectMenuByPage");
        PageHelper.startPage(pageNum, pageSize);
        List<DictionaryDto> resultList = dictionaryService.selectMenu(type, name);
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        return BaseResponse.buildSuccessResponse(new Pagination(new PageInfo(resultList)), resultList);
    }


    @ApiOperation(value = "分页返回所有数据", response = DictionaryDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的Activity", response = DictionaryDto.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/query")
    public BaseResponse selectAllByPage(
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "词典类型：public公共，private私有，all全部") String type,
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "词典名") String name,
            @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> selectAllByPage");
        PageHelper.startPage(pageNum, pageSize);
        List<DictionaryDto> resultList = dictionaryService.selectAll(type, name);
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        return BaseResponse.buildSuccessResponse(new Pagination(new PageInfo(resultList)), resultList);
    }

    @ApiOperation(value = "根据父词典返回树结构返回列表", response = DictionaryDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/query/tree")
    public BaseResponse selectTreeByParentKey(
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "父词典key，为空返回所有") String parentKey) {
        logger.debug(">> selectTreeByParentKey");
        List<DictionaryDto> resultList;
        if(StringUtils.isEmpty(parentKey)) {
            resultList = dictionaryService.getDicTreeByParentId("", true);
        } else {
            resultList = dictionaryService.getDicTreeByParentId(parentKey, true);
        }
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        return BaseResponse.buildSuccessResponse(resultList);
    }

    @ApiOperation(value = "根据父类id分页返回列表", response = DictionaryDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的Activity", response = DictionaryDto.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/query/page")
    public BaseResponse selectAllByParentKeyPage(
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "词典名") String parentKey,
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "type级别:一级,二级，表示下级") String typeLevel,
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "名称") String name,
            @RequestParam(required = false, defaultValue = "") @ApiParam(value = "排序") String sort,
            @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> selectAllByPage");
        PageHelper.startPage(pageNum, pageSize);
        List<DictionaryDto> resultList = dictionaryService.selectAll(parentKey, typeLevel, sort, name);
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        return BaseResponse.buildSuccessResponse(new Pagination(new PageInfo(resultList)), resultList);
    }

    @ApiOperation(value = "根据id获取Dictionary信息", response = DictionaryDto.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的Dictionary", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public BaseResponse getDictionaryById(@PathVariable @ApiParam(required = true, value = "词典id") String id) {
        logger.debug(">> getDictionaryById");
        try {
            DictionaryDto dictionaryDto = dictionaryService.getDtoById(id);
            if (dictionaryDto == null) {
                throw new InvalidParamsException("请求失败：找不到id=" + id + "的Dictionary");
            }
            return BaseResponse.buildSuccessResponse(dictionaryDto);
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "查询数据失败");
        }
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("更新")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse updateDictionary(@RequestBody @ApiParam(value = "新Dictionary信息", required = true) DictionaryDto dictionary) {
        logger.debug(">> updateDictionary");
        Dictionary entity = dictionaryService.getEntityById(dictionary.getDicKey());
        if (entity == null) {
            throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        if (dictionary.sortOrder.equals(0) && !dictionary.sortOrder.equals(entity.getSortOrder())) {
            dictionary.setSortOrder(entity.getSortOrder());
        }
        dictionaryService.update(dictionary);
        if (dictionary.getDicKey().equals("PRODUCT_TYPE")) {
            cacheService.delete(KEY_MENUS);
        }
        return BaseResponse.buildSuccessResponse("更新成功");
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("创建")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.POST, consumes = {APPLICATION_JSON_UTF8_VALUE})
    public BaseResponse insertDictionary(@RequestBody @ApiParam(value = "创建Dictionary", required = true) DictionaryDto dictionary) {
        logger.debug(">> insertDictionary");
        DictionaryDto dictionaryDto = dictionaryService.insert(dictionary);
        if (dictionary.getDicKey().equals("PRODUCT_TYPE")) {
            cacheService.delete(KEY_MENUS);
        }
        return BaseResponse.buildSuccessResponse(dictionaryDto);
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("创建社区配置词典数据（电话、上下班时间..）")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.PUT, value = "/config/tenant")
    public BaseResponse insertTenantIdCofig(
            @RequestParam() @ApiParam(value = "类型:BAN_GONG_DIAN_HUA：办公电话 JIN_JI_DIAN_HUA： " +
                    "紧急电话 SHANG_BAN_SHI_JIAN：上班时间" +
                    " XIA_BAN_SHI_JIAN：下班时间BAN_GONG_SHUO_MING：办公时间说明 AN_QUAN_WEI_LAN：安全登录电子围栏") String type,
            @RequestBody(required = false) @ApiParam(value = "数据：data") StringParams data) {
        logger.debug(">> insertDictionary");
        String dataString = data == null ? "" : data.getData();
        DictionaryDto dictionaryDto = dictionaryService.insertConfigDic(type, dataString);
        return BaseResponse.buildSuccessResponse(dictionaryDto);
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "删除指定Dictionary")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public BaseResponse deleteDictionary(@PathVariable @ApiParam(value = "Dictionaryid", required = true) String id) {
        logger.debug(">> deleteDictionary");
        DictionaryDto dictionaryDto = dictionaryService.getDtoById(id);
        if (dictionaryDto != null) {
            dictionaryService.deleteById(id);
            if (dictionaryDto.getDicKey().equals("PRODUCT_TYPE")) {
                cacheService.delete(KEY_MENUS);
            }
        }

        return BaseResponse.buildSuccessResponse("数据删除成功");
    }

    @ApiOperation(value = "根据租户id获得指定类型下的所有子结点")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = DictionaryDto.class, responseContainer = "List")})
    @RequestMapping(method = RequestMethod.GET, value = "/children/{parentKey}")
    public BaseResponse getChildrenByParentKey2(@PathVariable @ApiParam(value = "parentKey", required = true) String parentKey,
                                                @RequestParam(required = false, defaultValue = "false")  @ApiParam(value = "是否使用缓存", defaultValue = "false") Boolean cache) {
        logger.debug(">> getChildrenByParentKey2");
        return this.getChildrenByParentKey(parentKey, cache);
    }

    @ApiOperation(value = "根据租户id获得指定类型下的所有子结点")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = DictionaryDto.class, responseContainer = "List")})
    @RequestMapping(method = RequestMethod.GET, value = "/{parentKey}/children")
    public BaseResponse getChildrenByParentKey(@PathVariable @ApiParam(value = "parentKey", required = true) String parentKey,
                                               @RequestParam(required = false, defaultValue = "false") @ApiParam(value = "是否使用缓存", defaultValue = "false") Boolean cache) {
        logger.debug(">> getChildrenByParentKey");
        try {
            return BaseResponse.buildSuccessResponse(dictionaryService.getByParent(parentKey, null, cache));
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "数据查询失败");
        }
    }

    @TokenAuthCheck
    @ApiOperation(value = "根据租户id获得搜索栏里初始化数据")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：原因", response = DictionaryDto.class, responseContainer = "List")})
    @RequestMapping(method = RequestMethod.GET, value = "/t/{tid}/searchInitData")
    public BaseResponse getInitData(@PathVariable @ApiParam(value = "租户id", required = true) String tid) {
        logger.debug(">> getChildrenByParentKey");
        try {
            List<DictionaryDto> resutlList = new ArrayList<>();
//          添加设施类型
            resutlList.addAll(dictionaryService.getByParent("FACILITIES_TYPE", null, true));
//          添加生活圈类型
            resutlList.addAll(dictionaryService.getByParent("LIVING_CIRCLE_TYPE", null, true));
//          添加企业类型
            resutlList.addAll(dictionaryService.getByParent("ORG_COMPANY_TYPE", null, true));
            return BaseResponse.buildSuccessResponse(resutlList);
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "数据查询失败");
        }
    }

    @ApiOperation(value = "获取教材版本、科目、艺术表现形式")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：原因", response = DictionaryDto.class, responseContainer = "List")})
    @RequestMapping(method = RequestMethod.GET, value = "/getMaterial")
    public BaseResponse getTeachInfoData(@RequestParam(required = false) @ApiParam(value = "materials、objects、artTypes、grades") String key,
                                         @RequestParam(required = false, defaultValue = "false") @ApiParam(value = "是否需要刷新缓存") Boolean needRefresh) {
        logger.debug(">> getChildrenByParentKey");
        try {
            return BaseResponse.buildSuccessResponse(dictionaryService.getTeachInfo(key, needRefresh));
        } catch (OpenmoreException e) {
            return BaseResponse.buildFailResponse(400, e.getMsg());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return BaseResponse.buildFailResponse(400, "数据查询失败");
        }
    }
}




