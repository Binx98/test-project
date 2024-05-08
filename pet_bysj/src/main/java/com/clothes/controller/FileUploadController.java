package com.clothes.controller;

import com.clothes.utils.MinioUtil;
import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Author
 * @Date: 2024/11/16 19:46
 * @Version 1.0
 * @Description: 文件上传
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    private MinioUtil minioUtil;

    /**
     * 文件上传功能
     * http://101.42.13.186:9000/avatar-bucket/1700135638634_1700135821321.jpg
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws Exception {
        String url = minioUtil.upload(file, "bysj");
        return R.out(ResponseEnum.SUCCESS, url);
    }
}
