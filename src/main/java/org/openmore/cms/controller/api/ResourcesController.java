package org.openmore.cms.controller.api;

import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.*;
import org.openmore.cms.controller.common.BaseController;
import org.openmore.cms.dto.StringParams;
import org.openmore.cms.entity.Resources;

import org.openmore.cms.dto.common.BaseResponse;
import org.openmore.cms.dto.common.Pagination;
import org.openmore.cms.entity.enums.ResourceType;
import org.openmore.common.storage.AliyunCloudStorageService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;

import org.openmore.common.annotation.TokenAuthCheck;
import org.openmore.cms.service.ResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.openmore.cms.dto.api.ResourcesDto;
import org.openmore.common.exception.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore
 * on 2018-12-21
 */
@Api(value = "/Resources", tags = "", description = "资源库操作")
@RequestMapping(value = "/api/resources", produces = {APPLICATION_JSON_UTF8_VALUE})
@RestController
public class ResourcesController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private AliyunCloudStorageService aliyunCloudStorageService;

    @TokenAuthCheck
    @ApiOperation(value = "分页返回所有数据", response = ResourcesDto.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = ResourcesDto.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/query")
    public BaseResponse selectAllByPage(@RequestParam(required = false) @ApiParam(value = "资源名称") String name,
                                        @RequestParam(required = false) @ApiParam(value = "资源存储key") String key,
                                        @ApiParam(value = "资源类型:IMAGE:图片、AUDIO：音频、VIDEO：视频、OTHERS:其它类型") ResourceType type,
                                        @RequestParam(required = false) @ApiParam(value = "开始时间") Date startTime,
                                        @RequestParam(required = false) @ApiParam(value = "结束时间") Date endTime,
                                        @RequestParam(required = false, defaultValue = "1") @ApiParam(value = "分页第几页") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") @ApiParam(value = "每页多少记录") Integer pageSize) {
        logger.debug(">> selectAllByPage");

        PageHelper.startPage(pageNum, pageSize);
        List<ResourcesDto> resultList = resourcesService.selectAll(name, key, type, startTime, endTime);
        if (resultList == null) {
            throw new InvalidParamsException("没有数据");
        }
        PageInfo pageInfo = new PageInfo(resultList);
        return BaseResponse.buildSuccessResponse(new Pagination(pageInfo), resultList);
    }

    @TokenAuthCheck
    @ApiOperation(value = "根据id获取信息", response = ResourcesDto.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：找不到id={id}的", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public BaseResponse getResourcesById(@PathVariable @ApiParam(required = true, value = "id") String id) {
        logger.debug(">> getResourcesById");
        ResourcesDto resourcesDto = resourcesService.getDtoById(id);
        if (resourcesDto == null) {
            throw new InvalidParamsException("请求失败：找不到id=" + id + "的");
        }
        return BaseResponse.buildSuccessResponse(resourcesDto);
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("更新")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：更新的数据不存在", response = BaseResponse.class),
            @ApiResponse(code = 400, message = "请求失败：数据更新失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse updateResources(@RequestBody @ApiParam(value = "新信息", required = true) ResourcesDto resources) {
        logger.debug(">> updateResources");
        Resources entity = resourcesService.getEntityById(resources.getId());
        if (entity == null) {
            throw new InvalidParamsException("请求失败：更新的数据不存在");
        }
        resourcesService.update(resources);
        return BaseResponse.buildSuccessResponse("更新成功");

    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("创建视频资源")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse addVideoResources(@RequestBody @ApiParam(value = "新资源", required = true) ResourcesDto resources) {
        logger.debug(">> addVideoResources");
        ResourcesDto dto = resourcesService.insert(resources);
        return BaseResponse.buildSuccessResponse(dto);

    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("批量创建")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.POST, value = "/files")
    public BaseResponse insertResources(HttpServletRequest request, @RequestParam(value = "files") MultipartFile[] files) {
        logger.debug(">> insertResources");
        if (null == files || files.length <= 0) {
            return BaseResponse.buildFailResponse(4001, "文件不能为空");
        }
        List<ResourcesDto> resourcesDtos = resourcesService.insertFiles(files);
        if (Collections.isEmpty(resourcesDtos)) {
            return BaseResponse.buildFailResponse(4001, "上传失败");
        }
        return BaseResponse.buildSuccessResponse(resourcesDtos);
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation("单文件上传")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.POST, value = "/file")
    public BaseResponse insertResource(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file) {
        logger.debug(">> insertResources");
        if (null == file) {
            return BaseResponse.buildFailResponse(4001, "文件不能为空");
        }
        String fileLength = request.getHeader("Content-Length");
        ResourcesDto resourcesDto = resourcesService.insertFile(file, fileLength);
        if (null == resourcesDto) {
            return BaseResponse.buildFailResponse(4001, "上传失败");
        }
        return BaseResponse.buildSuccessResponse(resourcesDto);
    }

//    @TokenAuthCheck
    @Transactional
    @ApiOperation("获得直传Token")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/uploadToken")
    public BaseResponse getUploadToken() {
        logger.debug(">> getUploadKey");
        return BaseResponse.buildSuccessResponse(resourcesService.directUpload());
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "物理删除阿里云图片")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.POST, value = "/oss/physical")
    public BaseResponse deleteOssResource(@RequestBody @ApiParam(value = "oss对像名")StringParams fileName) {
        logger.debug(">> deleteResources");
        aliyunCloudStorageService.deleteFile(fileName.getData());
        return BaseResponse.buildSuccessResponse("数据删除成功");
    }


    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "删除指定资源")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public BaseResponse deleteResources(@PathVariable @ApiParam(value = "id", required = true) String id) {
        logger.debug(">> deleteResources");
        resourcesService.deleteById(id);
        return BaseResponse.buildSuccessResponse("数据删除成功");
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "下载指定资源")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据删除失败", response = BaseResponse.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/download")
    public BaseResponse downloadResources(HttpServletResponse response, @PathVariable @ApiParam(value = "id", required = true) String id) {
        logger.debug(">> deleteResources");
        ResourcesDto resourcesDto = resourcesService.getDtoById(id);
        if (null == resourcesDto || resourcesDto.getId() == null) {
            return BaseResponse.buildSuccessResponse("资源不存在");
        }
        aliyunCloudStorageService.download(response, resourcesDto.getKey(), resourcesDto.getName());
        return BaseResponse.buildSuccessResponse("数据下载成功");
    }

//    @TokenAuthCheck
    @Transactional
    @ApiOperation("oss直传Token回调")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "请求失败：数据创建失败", response = ResourcesDto.class)})
    @RequestMapping(method = RequestMethod.POST, value = "/uploadCallback", produces = {"application/json;charset=UTF-8"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BaseResponse uploadCallback(
            @RequestParam(required = false) @ApiParam(value = "文件名") String filename,
            @RequestParam(required = false) @ApiParam(value = "文件大小") String size,
            @RequestParam(required = false) @ApiParam(value = "文件类型") String mimeType,
            @RequestParam(required = false) @ApiParam(value = "高度") String height,
            @RequestParam(required = false) @ApiParam(value = "宽度") String width
    ) {
        logger.debug(">> uploadCallback");
//        String autorizationInput = new String(request.getHeader("Authorization"));
//        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
//        String queryString = request.getQueryString();
//        String uri = request.getRequestURI();
//        String ossCallbackBody = null;
//        int contentLen = Integer.parseInt(request.getHeader("content-length"));
        try{
//            InputStream is = request.getInputStream();
//            if (contentLen > 0) {
//                int readLen = 0;
//                int readLengthThisTime = 0;
//                byte[] message = new byte[contentLen];
//                while (readLen != contentLen) {
//                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
//                    if (readLengthThisTime == -1) {// Should not happen.
//                        break;
//                    }
//                    readLen += readLengthThisTime;
//                }
//                ossCallbackBody = new String(message);
            return BaseResponse.buildSuccessResponse(aliyunCloudStorageService.uploadCallback(null, null, null, null, filename, size, mimeType, width, height));
        }catch (Exception e){
        }
        return BaseResponse.buildFailResponse(500, "资源不存在");
    }

    @TokenAuthCheck
    @Transactional
    @ApiOperation(value = "导入保利数据", response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 4000, message = "请求失败：原因", response = String.class)})
    @RequestMapping(method = RequestMethod.POST, value = "/parseExcelPolvs", produces = {APPLICATION_JSON_UTF8_VALUE})
    public BaseResponse parseExcelProducts(@RequestParam() @ApiParam(value = "excel文件") MultipartFile file) {
        logger.debug(">> parseExcelProducts");
        return BaseResponse.buildSuccessResponse(resourcesService.parsePolvVideo(file));
    }
    //@TokenAuthCheck
    @Transactional
    @ApiOperation(value = "下载保利数据导入模板", response = String.class)
    @ApiResponses(value = {@ApiResponse(code = 4000, message = "请求失败：原因", response = String.class)})
    @RequestMapping(method = RequestMethod.GET, value = "/parsePolvExcelModel")
    public BaseResponse parseExcelModel(HttpServletResponse response) {
        logger.debug(">> parseExcelModel");
        try {
            resourcesService.createPolvExcelModel(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.buildSuccessResponse("导出成功");
    }
}




