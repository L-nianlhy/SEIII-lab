package com.example.collect.service.file;

import com.example.collect.vo.ResultVO;
import com.example.collect.vo.file.FileVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件传输相关service方法
 */

public interface FileService {
    // 上传测试app
    ResultVO<FileVO> uploadApp(Integer taskId, MultipartFile multipartFile) throws Exception;

    // 上传测试doc
    ResultVO<FileVO> uploadDoc(Integer taskId, MultipartFile multipartFile) throws Exception;

    // 上传缺陷截图
    ResultVO<FileVO> uploadPhoto(Integer reportId, MultipartFile multipartFile) throws Exception;

    // 根据objectName获取文件url
    String download(String objectName);

    // 获取测试App下载url
    String downloadApp(Integer taskId);

    // 获取测试文档下载url
    String downloadDoc(Integer taskId);

    // 获取缺陷截图下载url
    String downloadPhoto(Integer reportId);

    // 删除文件
    void removeFile(String objectName);
}
