package org.openmore.common.storage;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;

/**
 * 云存储接口
 */
public interface CloudStorageService {

    /**
     * 上传文件到云存储, 返回图片HTTP地址
     *
     * @param data     字节数据
     * @param fileKey 云存路径+名称
     * @return 图片HTTP地址
     * @throws Exception
     */
    String upload(byte[] data, String fileKey) throws Exception;

    /**
     * 上传文件到云存储, 返回图片HTTP地址
     *
     * @param inputStream 字节流
     * @param fileKey 云存路径+名称
     * @return 图片HTTP地址
     * @throws Exception
     */
    String upload(InputStream inputStream, String fileKey) throws Exception;

    /**
     * 上传文件到云存储, 返回图片HTTP地址
     *
     * @param multiFile 文件
     * @param fileKey 云存路径+名称
     * @return 图片HTTP地址
     * @throws Exception
     */
    String upload(MultipartFile multiFile, String fileKey);

    /**
     * 附件文件下载
     */
    void download(HttpServletResponse response, String fileKey, String fileName);
}