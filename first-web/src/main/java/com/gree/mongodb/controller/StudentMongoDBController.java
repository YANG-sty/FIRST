package com.gree.mongodb.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gree.first.annotation.Monit;
import com.gree.first.mongodb.DocumentDateService;
import com.gree.first.mongodb.DocumentFileService;
import com.gree.first.moniter.MonitLog;
import com.gree.first.student.dto.Student;
import com.gree.monit.MonitListContent;
import com.gree.utils.Watermark;
import com.itextpdf.text.DocumentException;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangLongFei 2021-01-29-15:04
 */
@Api(value = "mongodbStudent操作")
@RestController
@RequestMapping("/api/student/mongodb/")
public class StudentMongoDBController {

    @Autowired
    private DocumentDateService documentDateService;

    @Autowired
    private DocumentFileService documentFileService;


    @ApiOperation("新增")
    @PostMapping("/addStudent")
    public Student addStudent(@Valid @RequestBody Student student) {
        Student save = documentDateService.save(student);
        return save;
    }

    @ApiOperation("查询")
    @GetMapping("/selectStudentPage")
    public Page selectStudent(@RequestBody Student student) {
        Page page = documentDateService.selectList(student);
        return page;
    }

    @ApiOperation("修改")
    @PostMapping("/updateStudent")
    public long updateStudent(@Valid @RequestBody Student student) {
        return documentDateService.updateById(student);
    }


    @ApiOperation("删除")
    @PostMapping("/deleteStudent")
    public Integer deleteStudent(@Valid @RequestBody Student student) {
        return documentDateService.delete(student);
    }


    @ApiOperation("上传文件")
    @PostMapping("/uploadFile") //http://localhost:8081/greeFIRST/api/student/mongodb/uploadFile
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String s = documentFileService.uploadFile(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), null, Student.class);
        return s;
    }

    @ApiOperation("根据id下载附件")
    @GetMapping("/downLoadById") //http://localhost:8081/greeFIRST/api/student/mongodb/downLoadById?fieldId=6017bac5bdf15736f80e4ac6
    @Monit(fieldId = "#fieldId") //参数值要和 String fieldId 保持一致
    public void downLoadById(@RequestParam(value = "fieldId") String fieldId, HttpServletResponse response, HttpServletRequest request) throws IOException {

        GridFSDBFile gridFSDBFile = documentFileService.downloadFiledById(fieldId);
        InputStream inputStream = gridFSDBFile.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            byte[] bytes = new byte[1024];
            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            String filename = gridFSDBFile.getFilename();
            if (userAgent.contains("firefox")) {
                filename = new String(filename.getBytes(Charset.defaultCharset()), StandardCharsets.ISO_8859_1);
            } else {
                filename = new String(filename.getBytes(Charset.defaultCharset()), StandardCharsets.UTF_8);
            }
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            while (inputStream.read(bytes) > 0) {
                outputStream.write(bytes);
            }
        } finally {
            inputStream.close();
            outputStream.close();
        }

    }


    @ApiOperation("根据id 预览PDF，图片")
    @GetMapping("/previewById") //http://localhost:8081/greeFIRST/api/student/mongodb/previewById?fieldId=6017bac5bdf15736f80e4ac6
    @Monit(fieldId = "#fieldId") //参数值要和 String fieldId 保持一致
    public void previewById(@RequestParam(value = "fieldId") String fieldId, HttpServletResponse response) throws IOException {

        GridFSDBFile gridFSDBFile = documentFileService.downloadFiledById(fieldId);
        InputStream inputStream = gridFSDBFile.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        try {
            String filename = gridFSDBFile.getFilename();
            //添加水印,pdf、图片
            Watermark.waterMark(outputStream, inputStream,"yang_zzu");
            //PDF添加一个水印
//            Watermark.setWatermarkPDFSingleFill(outputStream, inputStream, "yang_zzu");
            //pdf自定义水印
//            Watermark.setWatermarkPDFSingleFill(outputStream, inputStream, "yang_zzu_3.14159271332224445555",30, 50);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStream.close();
            outputStream.close();
        }

    }


    @GetMapping("/getMonitLogList") //http://localhost:8081/greeFIRST/api/student/mongodb/getMonitLogList
    @ApiOperation("获得监控数据")
    public List<MonitLog> getMonitLogList() {
        return MonitListContent.monitLogList;
    }



}
