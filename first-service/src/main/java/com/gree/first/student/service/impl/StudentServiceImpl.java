package com.gree.first.student.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHanlderResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.gree.first.student.service.StudentService;
import com.gree.first.student.dto.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.InputStream;
import java.util.*;

/**
 * Create by yang_zzu on 2020/7/8 on 11:02
 */
@Service
public class StudentServiceImpl implements StudentService {


    @Override
    public List<Student> importAll(InputStream inputStream) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        //是否需要基础校验
        importParams.setNeedVerfiy(true);
        //自定义函数校验
        importParams.setVerifyHanlder(new IExcelVerifyHandler<Student>() {
            @Override
            public ExcelVerifyHanlderResult verifyHandler(Student student) {

                //保存提示信息
                StringBuilder res = new StringBuilder();

                try {
                    //做一些字段的验证判断内容
                    if (!"China".equals(student.getAddress())) {
                        res.append("输入的地址有误！！！");
                    }
                    //.......等其他的判断操作

                } catch (Exception e) {
                    res.append(e.getMessage());
                }

                return new ExcelVerifyHanlderResult(StringUtils.isEmpty(String.valueOf(res)), res.toString());
            }
        });

        ExcelImportResult<Student> excelImportResult = ExcelImportUtil.importExcelMore(inputStream, Student.class, importParams);
        //异常数据
        List<Student> failList = excelImportResult.getFailList();
        //需要导入的数据，这些是正常的数据
        List<Student> list = excelImportResult.getList();

        /**
         * 下面的操作是，获得，后面没有读取到的数据
         */
        //获得失败的 excle 工作表，第一个工作表
        Sheet sheetAt = excelImportResult.getFailWorkbook().getSheetAt(0);
        //获得最后一行的行号
        int lastRowNum = sheetAt.getLastRowNum();

        //列
        String cell0_value;
        double cell1_value;
        Date cell2_value;
        String cell3_value;

        //保存异常信息
        StringBuffer stringBuffer;
        //保存异常的对象信息
        Student studentFail;
        for (int i = 0; i < lastRowNum; i++) {
            studentFail = new Student();
            stringBuffer = new StringBuffer();
            Student student = new Student();
            Row row = sheetAt.getRow(i);
            Cell cell = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);

            //根据excel 该列设置的数据类型，进行不同数据的获取
            cell0_value = cell.getStringCellValue();
            cell1_value = cell.getNumericCellValue();
            cell2_value = cell.getDateCellValue();
            cell3_value = cell.getStringCellValue();

            //现在已经获得 第 i 行的，每个单元格的数据
            if ("XiaoMing".equals(cell0_value)) {
                stringBuffer.append("XiaoMing 已经存在！！！！");
            }
            studentFail.setName(cell0_value);
            studentFail.setAge((int) cell1_value);
            //......
            //将错误信息放到对象里面
            studentFail.setErrorMsg(stringBuffer.toString());

            //将 有问题的数据，加入到 链表中
            failList.add(studentFail);
        }

        if (failList.isEmpty()) {
            //如果 异常数据的链表为空，则说明 excel 表格中的数据全部符合导入的需求，进行正常的保存操作就可以了

            //...................

        }

        return excelImportResult.getFailList();
    }

    /**
     * 设置缓存
     * methodName 当前方法名 #root.methodName
     * method 当前方法 #root.method.name
     * target 当前被调用的对象 #root.target
     * targetClass 当前被调用的对象的class #root.targetClass
     * args 当前方法参数组成的数组 #root.args[0]
     * caches 当前被调用的方法使用的Cache #root.caches[0].name
     *
     * 缓存的值为 ，该方法的返回值
     */
    @Override
    @Cacheable(cacheNames = "CONSTANT" , key="#root.methodName + #url")
    public List<String> getResource(String url) {
        System.out.println(" 获得用户 url 的数据。。。。。。。。。。。");
        List<String> list = new ArrayList<>();
        list.add("byCar-0");
        return list;
    }


    @Override
    public Student getpassword(String name) {
        Student student = new Student();
        student.setName(name);
        student.setPassword("0");
        return student;
    }
}
