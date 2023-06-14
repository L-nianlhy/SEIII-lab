package com.example.collect.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

/**
 * OSS上传及下载文件
 */
@Repository
public class FileHelper {

    // Bucket所在地域对应的Endpoint
    private static String endpoint = "https://oss-cn-shanghai.aliyuncs.com";

    // 阿里云账号AccessKey
    private static String accessKeyId = "LTAI5tA7tSJUm4QGYheTWb9x";
    private static String accessKeySecret = "rIcYrQGJ3BaNU90ix5i3w7AC6DM5NF";

    // Bucket名称
    private static String bucketName = "project-collect";

    // 文件名前缀, 包含路径
    private static String firstKey = "collect";


    /**
     * 上传文件
     * @param multipartFile 存储的文件
     * @return 返回objectName存储在数据库中
     */
    public String uploadFile(MultipartFile multipartFile) throws Exception {
        File file = multipartFileToFile(multipartFile);
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置object名
        String objectName = firstKey + "/" + file.getName();
        ossClient.putObject(bucketName, objectName, file);
        deleteTempFile(file);
        // 关闭OSSClient
        ossClient.shutdown();
        return objectName;
    }

    /**
     * 获取文件url
     * @param objectName 获取OSS中的对象
     * @return 返回文件访问url
     */
    public String getDownloadUrl(String objectName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        // 关闭OSSClient
        ossClient.shutdown();
        return url.toString();
    }

    /**
     * 删除OSS中文件
     * @param objectName
     */
    public void removeObject(String objectName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }

    /**
     * MultipartFile转File
     * @param multipartFile
     * @return
     * @throws Exception
     */
    private static File multipartFileToFile(MultipartFile multipartFile) throws Exception {
        File toFile = null;
        if(Objects.equals(multipartFile.getOriginalFilename(), "") ||multipartFile.getSize()<=0){
            multipartFile = null;
        }else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            toFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            toFile = inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    private static File inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除临时文件
     * @param file
     */
    private static void deleteTempFile(File file){
        if(file != null){
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
