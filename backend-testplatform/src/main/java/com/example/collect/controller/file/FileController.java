package com.example.collect.controller.file;

import com.example.collect.serviceImpl.file.FileServiceImpl;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.file.FileVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件传输功能controller层接口
 */

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Resource
    private FileServiceImpl fileService;

    /**
     * 下载待测应用
     */
    @GetMapping("/downloadApp/{taskId}")
    public String downloadApp(@PathVariable Integer taskId){
        return fileService.downloadApp(taskId);
    }

    /**
     * 下载测试文档
     */
    @GetMapping("/downloadDoc/{taskId}")
    public String downloadDoc(@PathVariable Integer taskId){
        return fileService.downloadDoc(taskId);
    }

    /**
     * 下载缺陷截图
     */
    @GetMapping("/downloadPhoto/{reportId}")
    public String downloadPhoto(@PathVariable Integer reportId){
        return fileService.downloadPhoto(reportId);
    }

    /**
     * 上传测试应用
     */
    @PostMapping("/uploadApp/{taskId}")
    public ResultVO<FileVO> uploadApp(@PathVariable Integer taskId, @RequestParam("taskApp") MultipartFile taskApp) throws Exception {
        return fileService.uploadApp(taskId, taskApp);
    }

    /**
     *上传测试文档
     */
    @PostMapping("/uploadDoc/{taskId}")
    public ResultVO<FileVO> uploadDoc(@PathVariable Integer taskId, @RequestParam("taskDoc") MultipartFile taskDoc) throws Exception {
        return fileService.uploadDoc(taskId, taskDoc);
    }

    /**
     * 上传缺陷截图
     */
    @PostMapping("/uploadPhoto/{reportId}")
    public ResultVO<FileVO> uploadPhoto(@PathVariable Integer reportId, @RequestParam("bugPhoto") MultipartFile bugPhoto) throws Exception {
        return fileService.uploadPhoto(reportId, bugPhoto);
    }
}
