package com.example.collect.serviceImpl.file;

import com.example.collect.dao.report.ReportMapper;
import com.example.collect.dao.task.TaskMapper;
import com.example.collect.service.file.FileService;
import com.example.collect.util.Constant;
import com.example.collect.util.FileHelper;
import com.example.collect.vo.ResultVO;
import com.example.collect.vo.file.FileVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件传输相关service方法实现
 */

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private FileHelper fileHelper;

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private ReportMapper reportMapper;

    /**
     * 上传测试app
     * @param multipartFile 上传的测试文件
     * @return 返回上传的ObjectName
     */
    @Override
    public ResultVO<FileVO> uploadApp(Integer taskId, MultipartFile multipartFile) throws Exception {
        String taskApp = fileHelper.uploadFile(multipartFile);
        taskMapper.updateAppByPrimaryKey(taskId, taskApp);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功上传测试应用");
    }

    /**
     * 上传测试文档
     * @param taskId
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @Override
    public ResultVO<FileVO> uploadDoc(Integer taskId, MultipartFile multipartFile) throws Exception {
        String taskDoc = fileHelper.uploadFile(multipartFile);
        taskMapper.updateDocByPrimaryKey(taskId, taskDoc);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功上传测试文档");
    }

    /**
     * 上传缺陷截图
     * @param reportId
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @Override
    public ResultVO<FileVO> uploadPhoto(Integer reportId, MultipartFile multipartFile) throws Exception {
        String bugPhoto = fileHelper.uploadFile(multipartFile);
        reportMapper.updateBugPhoto(reportId, bugPhoto);
        return new ResultVO<>(Constant.REQUEST_SUCCESS, "成功上传缺陷截图");
    }

    /**
     * 根据objectName获取文件
     * @param objectName
     * @return
     */
    @Override
    public String download(String objectName) {
        return fileHelper.getDownloadUrl(objectName);
    }

    /**
     * 获得测试App的url
     * @param taskId 测试id
     * @return 返回url
     */
    @Override
    public String downloadApp(Integer taskId){
        String objectName = taskMapper.selectTaskApp(taskId);
        return fileHelper.getDownloadUrl(objectName);
    }

    /**
     * 获取测试文档的url
     * @param taskId 测试id
     * @return 返回url
     */
    @Override
    public String downloadDoc(Integer taskId) {
        String objectName = taskMapper.selectTaskDoc(taskId);
        return fileHelper.getDownloadUrl(objectName);
    }

    /**
     * 获取缺陷截图的url
     * @param reportId 报告id
     * @return 返回url
     */
    @Override
    public String downloadPhoto(Integer reportId) {
        String objectName = reportMapper.selectPhoto(reportId);
        return fileHelper.getDownloadUrl(objectName);
    }

    /**
     * 删除文件
     * @param objectName
     */
    @Override
    public void removeFile(String objectName) {
        fileHelper.removeObject(objectName);
    }
}
