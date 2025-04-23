package com.liu.project.utils;

import com.liu.project.exception.BaseException;
import com.liu.project.properties.MinioProperties;
import io.minio.*;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author LiuNing
 * <br><br/>
 * <a href="https://www.jb51.net/program/324384bkx.htm">连接</a>
 */
@Component
@Slf4j
public class MinioUtils {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    private static final String SEPARATOR = "/";

    /**
     * 获取文件上传的前缀
     *
     * @return 文件上传的前缀
     */
    public String getBasisUrl() {
        return minioProperties.getEndpoint() + SEPARATOR + minioProperties.getBucketName();
    }

    /**
     * 判断 Bucket 是否存在
     *
     * @param bucketName 桶
     */
    public boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 判断文件是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件名
     * @return exist
     */
    public boolean isObjectExists(String bucketName, String objectName) throws Exception {
        boolean exist = true;
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 存储桶
     * @param objectName 文件夹名
     * @return exist
     */
    public boolean isFolderExists(String bucketName, String objectName) throws Exception {
        boolean exist = false;
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).prefix(objectName).recursive(false).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && objectName.equals(item.objectName())) {
                    exist = true;
                }
            }
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 使用 MultipartFile 进行文件上传
     *
     * @param bucketName  存储桶
     * @param file        文件
     * @param dirPath     存储路径
     * @param objectName  对象名
     * @param contentType 类型
     * @return ObjectWriteResponse
     */
    public String uploadFile(String bucketName, MultipartFile file, String dirPath, String objectName, String contentType) {
        String filePath = dirPath + SEPARATOR + objectName;
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .contentType(contentType)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            return getBasisUrl() + filePath;
        } catch (Exception e) {
            log.error("文件上传失败");
            throw new BaseException(e.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName 存储桶
     * @param objectName 文件名称
     */
    public void removeFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }
}
