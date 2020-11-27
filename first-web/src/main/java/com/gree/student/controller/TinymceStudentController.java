package com.gree.student.controller;

import com.gree.first.bean.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Create by yang_zzu on 2020/10/1 on 16:26
 */
@Controller
@RequestMapping("/sutdent")
public class TinymceStudentController {

    /**
     * 将图片上传到 服务器
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadPicture")
    @ResponseBody
    public ResultVO uploadPicture(@RequestParam("edit") MultipartFile multipartFile) throws IOException {

        String substring = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        //新的文件名称
        String newFileName = UUID.randomUUID().toString() + substring;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        String fileMonth = simpleDateFormat.format(date);
        //图片路径
        String filePaht = "/var/img/" + fileMonth + "/" + newFileName;
        //文件初始化
        File newFile = new File(fileMonth);
        //如果该文件的 父文件夹 不存在则进行创建
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        multipartFile.transferTo(newFile);

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(200);
        resultVO.setMsg("ok");
        // data 数据为 文件的下载路径
        resultVO.setData("/student/downloadPicture/" + newFileName + "_" + fileMonth);

        return resultVO;
    }

    /**
     * 下载 服务器图片
     * @param id xxx.png_202010
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadPicture/{pictureId}")
    public void downloadPicture(@PathVariable("pictureId") String id, HttpServletResponse response) throws Exception {
        String[] s = StringUtils.split(id, "_");
        // 路径 / 月份 / 文件名称
        String url = "/var/img/" + s[1] + "/" + s[0];
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            response.setHeader("content-disposition", "attachment;filename="+ URLEncoder.encode(id, "UTF-8"));
            inputStream = new FileInputStream(url);
            int length = 0;
            byte[] buffer = new byte[1024];
            outputStream = response.getOutputStream();
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
