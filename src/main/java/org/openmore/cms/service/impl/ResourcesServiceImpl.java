package org.openmore.cms.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.openmore.cms.dto.api.ForeignResourceDto;
import org.openmore.cms.entity.ForeignResource;
import org.openmore.cms.entity.enums.Language;
import org.openmore.cms.entity.enums.ResourceType;
import org.openmore.cms.service.ForeignResourceService;
import org.openmore.cms.service.ResourcesService;

import org.openmore.cms.entity.Resources;
import org.openmore.cms.dao.ResourcesMapper;
import org.openmore.cms.dto.api.ResourcesDto;

import org.openmore.common.exception.ExceptionPrint;
import org.openmore.common.exception.OpenmoreException;
import org.openmore.common.polyv.PolyvService;
import org.openmore.common.polyv.PolyvVideo;
import org.openmore.common.storage.AliyunCloudStorageService;
import org.openmore.common.utils.ExportExcelUtils;
import org.openmore.common.utils.ParseExcelUtil;
import org.openmore.common.utils.ReferencedFieldProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;

import com.github.pagehelper.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResourcesServiceImpl extends BaseServiceImpl implements ResourcesService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourcesMapper resourcesMapper;


    @Autowired
    private ReferencedFieldProcessor processor;

    @Autowired
    private ForeignResourceService foreignResourceService;

    @Autowired
    private AliyunCloudStorageService aliyunCloudStorageService;

    @Autowired
    private PolyvService polyvService;

    @Autowired
    private ExceptionPrint exceptionPrint;

    @Value("${aliyun.oss.urlDomain}")
    private String urlDomain;

    /**
     * 图片文件匹配正则
     */
    private static final String IMAGE_MATCH = "^(.JPG)|(.JPEG)|(.PNG)|(.GIF)|(.BMP)|(.TIF)|(.SVG)|(.PSD)$";
    /**
     * 音频文件匹配正则
     */
    private static final String AUDIO_MATCH = "^(.MP3)|(.M4A)|(.WAV)|(.AMR)|(.AWB)|(.WMA)|(.OGG)|(.MID)|(.WMV)$";
    /**
     * 视频文件匹配正则
     */
    private static final String VIDEO_MATCH = "^(.VOB)|(.IOF)|(.MPG)|(.MPEG)|(.DAT)|(.MP4)|(.3GP)|(.MOV)|(.RM)|" +
            "(.RAM)|(.RMVB)|(.WMV)|(.ASF)|(.AVI)|(.ASX)$";


    /**
     * 将实体list转成带有分页的PageList
     *
     * @return
     */
    private Page<ResourcesDto> convertFrom(List<Resources> entitiesList) {
        Page<ResourcesDto> dtoPageList = new Page<>();
        if (entitiesList instanceof Page) {
            BeanUtils.copyProperties(entitiesList, dtoPageList);
            dtoPageList.clear();
        }

        for (Resources en : entitiesList) {
            ResourcesDto dto = new ResourcesDto();
            BeanUtils.copyProperties(en, dto);
            /*if(null!=dto.getType()){
                dto.setTypeName(dto.getType().getDisplayName());
            }*/
            //logger.debug("==>resource_type:"+ dto.getType().getValue());
            dtoPageList.add(dto);
        }
        List<ResourcesDto> dtoList = dtoPageList.getResult();
        processor.process(dtoList);
        return dtoPageList;
    }

    /**
     * 根据id获得实体对象
     *
     * @param id
     */
    @Override
    public Resources getEntityById(String id) {
        Example example = new Example(Resources.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
        return resourcesMapper.selectOneByExample(example);
    }

    /**
     * 根据id获得DTO对象
     *
     * @param id
     */
    @Override
    public ResourcesDto getDtoById(String id) {
        ResourcesDto dto = new ResourcesDto();
        Resources resources = this.getEntityById(id);
        if (resources == null) {
            return null;
        }
        BeanUtils.copyProperties(resources, dto);
        /*if(null!=dto.getType()){
            dto.setTypeName(dto.getType().getDisplayName());
        }*/
        processor.processOne(dto);
        return dto;
    }

    /**
     * 获得所有记录
     *
     * @return
     */
    @Override
    public List<ResourcesDto> selectAll(String name, String key, ResourceType fileType, Date startTime, Date endTime) {
        Example example = new Example(Resources.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(key)){
            criteria.andEqualTo("key", key);
        }
        if (!StringUtils.isEmpty(name)) {
            name = "%" + name + "%";
            criteria.andLike("name", name);
        }
        if (!StringUtils.isEmpty(fileType)) {
            criteria.andEqualTo("type", fileType);
        }
        if (null != startTime && null != endTime && endTime.before(startTime)) {
            endTime = null;
        }
        if (null != startTime) {
            criteria.andGreaterThanOrEqualTo("createdTime", startTime);
        }
        if (null != endTime) {
            criteria.andLessThanOrEqualTo("createdTime", endTime);
        }
        example.orderBy("createdTime").desc();
        List<Resources> entityList = resourcesMapper.selectByExample(example);

        return convertFrom(entityList);
    }

    @Override
    public Integer selectCount(String name, String key, ResourceType fileType, Date startTime, Date endTime) {
        Example example = new Example(Resources.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("deleted", false);
        if(!StringUtils.isEmpty(key)){
            criteria.andEqualTo("key", key);
        }
        if (!StringUtils.isEmpty(name)) {
            name = "%" + name + "%";
            criteria.andLike("name", name);
        }
        if (!StringUtils.isEmpty(fileType)) {
            criteria.andEqualTo("type", fileType);
        }
        if (null != startTime && null != endTime && endTime.before(startTime)) {
            endTime = null;
        }
        if (null != startTime) {
            criteria.andGreaterThanOrEqualTo("createdTime", startTime);
        }
        if (null != endTime) {
            criteria.andLessThanOrEqualTo("createdTime", endTime);
        }
        Integer count = resourcesMapper.selectCountByExample(example);
        return null == count ? 0 : count;
    }

    public Map<String, String> directUpload(){
        return aliyunCloudStorageService.directUpload();
    }

    /**
     * 带UUID的插入
     * 所有的插入数据，都要调用这个实现
     *
     * @param dto
     */
    @Override
    public ResourcesDto insert(ResourcesDto dto) {
        Resources entity = new Resources();
        BeanUtils.copyProperties(dto, entity);
        beforeInsert(entity);
        resourcesMapper.insert(entity);
        processor.processOne(dto);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 批量保存文件
     */
    @Override
    public List<ResourcesDto> insertFiles(MultipartFile[] files) {
        if (null == files || files.length <= 0) {
            return null;
        }
        List<ResourcesDto> resources = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            if (null != files) {
                String fileName = multipartFile.getOriginalFilename();
                String fileKey = aliyunCloudStorageService.getFileKey(fileName);
                String url = aliyunCloudStorageService.upload(multipartFile, fileKey);
                if (!StringUtils.isEmpty(url)) {
                    ResourcesDto resources1 = new ResourcesDto();
                    resources1.setKey(fileKey);
                    resources1.setName(fileName);
                    resources1.setUrl(urlDomain + fileKey);
                    resources1.setType(getResourceType(fileName));
                    resources1 = insert(resources1);
                    resources.add(resources1);
                }
            }
        }
        return resources;
    }

    @Override
    public ResourcesDto insertFile(MultipartFile file, String fileLength) {
        String fileName = file.getOriginalFilename();
        String fileKey = aliyunCloudStorageService.getFileKey(fileName);
        String url = aliyunCloudStorageService.upload(file, fileKey);
        if (!StringUtils.isEmpty(url)) {
            ResourcesDto resources1 = new ResourcesDto();
            resources1.setSize(fileLength);
            resources1.setKey(fileKey);
            resources1.setName(fileName);
            resources1.setUrl(urlDomain + "/" + fileKey);
            resources1.setType(getResourceType(fileName));
            return insert(resources1);
        }
        throw new OpenmoreException(4001, "上传失败");
    }

    /**
     * 删除指定id的数据
     */
    @Override
    public void deleteById(String id) {
        if(StringUtils.isEmpty(id)){
            throw new OpenmoreException(4001, "id不能为空");
        }
//        List<ArticleResourceDto> arDto = articleResourceService.selectAll(null, id, null);
//        if(arDto != null && arDto.size() > 0) {
//            throw new OpenmoreException(4001, "资源正在被使用中，无法删除！");
//        }
        Resources entity = this.getEntityById(id);
        if (entity != null) {
            Example example = new Example(Resources.class);
            example.createCriteria().andEqualTo("id", id).andEqualTo("deleted", false);
            entity.setDeleted(true);
            resourcesMapper.updateByExampleSelective(entity, example);
        }
    }

    /**
     * 修改数据
     */
    @Override
    public void update(ResourcesDto dto) {
        if (null == dto) {
            throw new OpenmoreException(4001, "参数不能为空");
        }
        if(StringUtils.isEmpty(dto.getId())){
            throw new OpenmoreException(4001, "id不能为空");
        }
        Resources entity = new Resources();
        BeanUtils.copyProperties(dto, entity);
        Example example = new Example(Resources.class);
        example.createCriteria().andEqualTo("id", entity.getId()).andEqualTo("deleted", false);
        resourcesMapper.updateByExampleSelective(entity, example);
    }

    public ResourceType getResourceType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        logger.debug("==>compare:" + fileExtension);
        if(fileExtension.toUpperCase().matches(IMAGE_MATCH)){
            return ResourceType.IMAGE;
        }
        else if(fileExtension.toUpperCase().matches(AUDIO_MATCH)){
            return ResourceType.AUDIO;
        }
        else if(fileExtension.toUpperCase().matches(VIDEO_MATCH)){
            return ResourceType.VIDEO;
        }
        return ResourceType.OTHERS;
    }

    @Override
    public List<ResourcesDto> selectResourcesByForeignId(String foreignId) {
        List<ForeignResourceDto> foreignResourceDtos = foreignResourceService.selectAll(null, foreignId, null);
        List<String> ids = parseArray(foreignResourceDtos);
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }
        Example example = new Example(Resources.class);
        example.createCriteria().andIn("id", ids);
        return convertFrom(resourcesMapper.selectByExample(example));
    }

    /**解析id列表*/
    private List<String> parseArray(List<ForeignResourceDto>  dtos){
        if(CollectionUtils.isEmpty(dtos)){
            return null;
        }
        List<String> array = new ArrayList<>();
        for(ForeignResourceDto dto : dtos){
            array.add(dto.getResourceId());
        }
        return array;
    }

    /**读取excel保利vid视频资源
     * 格式：
     * 视频名称 | 视频vid
     * 小露珠  |  123
     * */
    @Override
    public String parsePolvVideo(MultipartFile file){
        if (file == null) {
            throw new OpenmoreException("文件不能为空");
        }
        //获取文件名
        String fileName = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (StringUtils.isEmpty(fileName) && size == 0) {
            throw new OpenmoreException("文件不能为空");
        }
        if (!fileName.endsWith(".xls") && !fileName.endsWith("xlsx")) {
            logger.debug("==>文件名：" + fileName);
            throw new OpenmoreException("文件类型不正确");
        }

        InputStream in = null;
        try {
            // 同时支持Excel 2003、2007
            // 文件流
            in = file.getInputStream();
            //ParseExcelUtil.checkExcelVaild(file);
            Workbook workbook = new XSSFWorkbook(in);

            //设置当前excel中sheet的下标：0开始
            Sheet sheet = workbook.getSheetAt(0);

            List<ResourcesDto> resourcesDtos = new ArrayList<ResourcesDto>();
            List<String> oldKeys = new ArrayList<>();
            List<String> invalidKeys = new ArrayList<>();
            int count = 0;
            for (Row row : sheet) {
                ResourcesDto resourcesDto = new ResourcesDto();
                try {
                    // 跳过第一行的目录
                    if (count < 1) {
                        count++;
                        continue;
                    }
                    //如果当前行没有数据，跳出循环
                    if (row.getCell(0).toString().equals("")) {
                        //throw new OpenmoreException("没有数据");
                        continue;
                    }
                    //解析每列固定数据
                    Cell cell1 = row.getCell(0);
                    String title = null == cell1 ? null : ParseExcelUtil.getValue(cell1).toString();
                    Cell cell2 = row.getCell(1);
                    String vid = null == cell2 ? null : ParseExcelUtil.getValue(cell2).toString();
                    List<ResourcesDto> olds = selectAll(null, vid, null, null, null);
                    if(!CollectionUtils.isEmpty(olds)){
                        oldKeys.add(vid);
                    }
                    PolyvVideo polyvVideo = polyvService.getVideo(vid);
                    if(null == polyvVideo){
                        invalidKeys.add(vid);
                    }
                    resourcesDto.setName(title);
                    if(StringUtils.isEmpty(title)){
                        resourcesDto.setName(polyvVideo.getTitle());
                    }
                    resourcesDto.setKey(vid);
                    resourcesDto.setUrl(polyvVideo.getMp4_1());
                    resourcesDto.setSize(polyvVideo.getFilesize()[0]+"");
                    resourcesDto.setDuration(polyvVideo.getDuration());
                    resourcesDto.setType(ResourceType.VIDEO);
                    resourcesDtos.add(resourcesDto);
                } catch (OpenmoreException e) {
                    exceptionPrint.print(this.getClass(), e);
                    throw new OpenmoreException("第" + count + "条产品信息解析失败" + e.getMsg());
                } catch (Exception e) {
                    exceptionPrint.print(this.getClass(), e);
                    throw new OpenmoreException("第" + count + "条产品信息解析失败" + e.getMessage());
                }
            }
            try {
                //关闭流占用
                in.close();
            } catch (IOException e) {
                exceptionPrint.print(this.getClass(), e);
            }
            if (CollectionUtils.isEmpty(resourcesDtos)) {
                throw new OpenmoreException("未成功解析到数据");
            }
            //遍历保存数据，有一条出错需要重新提交
            int j = 0;
            for (ResourcesDto resourcesDto : resourcesDtos) {
                j++;
                try {
                    insert(resourcesDto);
                } catch (OpenmoreException e) {
                    throw new OpenmoreException(e.getMsg());
                } catch (Exception e) {
                    exceptionPrint.print(this.getClass(), e);
                    logger.error("add resident error:" + e.getMessage());
                    throw new OpenmoreException("第" + j + "条信息保存失败，请检查后重新提交");
                }
            }
            if(CollectionUtils.isEmpty(oldKeys)&& CollectionUtils.isEmpty(invalidKeys)){
                return "上传成功,保存["+resourcesDtos.size()+"]条数据";
            }else{
                return "已存在vid:["+ JSON.toJSONString(oldKeys) +"],非法vid:["+JSON.toJSONString(invalidKeys)+"]";
            }

        } catch (IOException e) {
            exceptionPrint.print(this.getClass(), e);
            throw new OpenmoreException("读取文件出错");
        }
    }

    /**创建保利上传模版*/
    @Override
    public void createPolvExcelModel(HttpServletResponse response){
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("产品导入模板.xlsx", "utf-8"));
            //创建HSSFWorkbook对象
            // 创建HSSFSheet对象
            XSSFSheet sheet = wb.createSheet("产品导入模板");
            // 创建HSSFRow对象
            XSSFRow row = sheet.createRow(0);
            //创建HSSFCell对象
            //XSSFCell cell1 = row.createCell(0);
            // 设置单元格的值
            //cell1.setCellValue("序号");

            //设置标题格式
            Font titleFont = wb.createFont();
            titleFont.setFontName("simsun");
            titleFont.setBold(true);
            // titleFont.setFontHeightInPoints((short) 14);
            titleFont.setColor(IndexedColors.BLACK.index);
            XSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
            titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(182, 184, 192)));
            titleStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            titleStyle.setFont(titleFont);
            ExportExcelUtils.setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new java.awt.Color(0, 0, 0)));

            XSSFCell cell2 = row.createCell(0);
            cell2.setCellValue("视频标题");
            cell2.setCellStyle(titleStyle);
            XSSFCell cell3 = row.createCell(1);
            cell3.setCellValue("视频VID");
            cell3.setCellStyle(titleStyle);
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            exceptionPrint.print(this.getClass(), e);
            throw new OpenmoreException("文件创建出错");
        } finally {
            try {
                wb.close();
                //response.getOutputStream().close();
            } catch (Exception e) {
                exceptionPrint.print(this.getClass(), e);
                throw new OpenmoreException("文件创建出错");
            }
        }
    }
}
