package com.liu.rbac.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.liu.rbac.constant.FileConstant;
import com.liu.rbac.utils.MinioUtils;
import com.liu.rbac.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/file")
@Api(tags = "文件管理")
public class FileController {
    @Resource
    private MinioUtils minioUtils;
    @Value("${minio.bucket-name}")
    private String bucketName;

    @ApiOperation(value = "上传头像")
    @ApiModelProperty(dataType = "MultipartFile", required = true, value = "图片")
    @PostMapping("/upload/avatar")
    private Result<String> uploadAvatar(@RequestPart("file") @NotNull(message = "文件不能为空") MultipartFile multipartFile) {
        // 1. 生成uuid
        String uuid = RandomUtil.randomString(16);
        // 2. 拼接文件名
        String fileName = String.format("%s.%s", uuid, FileUtil.getSuffix(multipartFile.getOriginalFilename()));
        // 3. 上传文件
        String url = minioUtils.uploadFile(bucketName, multipartFile, FileConstant.USER_AVATAR_PATH, fileName, multipartFile.getContentType());
        // 4. 返回url
        return Result.success(url);
    }
}
