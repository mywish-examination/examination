package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.*;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.SchoolService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/school")
public class SchoolController {

    @Resource
    private SchoolService schoolService;
    @Value("${examination.upload.school-url}")
    private String schoolUrl;

    @PostMapping("/listPage")
    @ResponseBody
    public SchoolPager listPage(SchoolPager pager) {
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        schoolService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<SchoolDO> listSuggest(SchoolDO schoolDO) {
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(schoolDO.getName()), " name like '%" + schoolDO.getName() + "%'");
        List<SchoolDO> list = schoolService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SchoolDO schoolDO = new SchoolDO();
        if (id != null) {
            schoolDO = schoolService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/school/modify");
        model.addAttribute("school", schoolDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolDO param) {
        ModelAndView mav = new ModelAndView("/pages/school/list");
        schoolService.saveOrUpdate(param);
        return mav;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFileImage(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("Filedata");
        String tempFileName = request.getParameter("Filename");
        String fileExtensionName = tempFileName.substring(tempFileName.lastIndexOf("."));
        String realFileName = UUID.randomUUID().toString() + fileExtensionName;
        String path = schoolUrl + realFileName;
        String filePath = request.getServletContext().getRealPath("/") + path;
        try (InputStream is = file.getInputStream();) {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            while ((is.read(b)) != -1) {
                fileOutputStream.write(b);
            }

            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/uploadFileExcel")
    @ResponseBody
    public String uploadFileExcel(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("Filedata");
        String tempFileName = request.getParameter("Filename");
        String fileExtensionName = tempFileName.substring(tempFileName.lastIndexOf("."));

        Workbook book = null;
        try {
            if (fileExtensionName.equals(".xls")) {
                book = new HSSFWorkbook(file.getInputStream());
            } else {
                book = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheetAt = book.getSheetAt(0);

        int lastRowNum = sheetAt.getLastRowNum();
        List<SchoolDO> list = new ArrayList<>();
        SchoolDO schoolDO;
        for (int i = 1; i <= lastRowNum; i++) {
            schoolDO = new SchoolDO();
            Row row = sheetAt.getRow(i);

            // 学校名称
            Cell cell = row.getCell(0);
            String name = cell.getStringCellValue();
            if (StringUtils.isEmpty(name)) continue;
            schoolDO.setName(name);

            // 学校主类型
            Cell cell1 = row.getCell(1);
            String mainType = cell1.getStringCellValue();
            schoolDO.setMainType(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_MAIN_TYPE.getCode(), mainType));

            // 学校子类型
            Cell cell2 = row.getCell(2);
            String childrenType = cell2.getStringCellValue();
            schoolDO.setChildrenType(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_CHILDREN_TYPE.getCode(), childrenType));

            // 曾用名
            Cell cell3 = row.getCell(3);
            String onceName = cell3.getStringCellValue();
            schoolDO.setOnceName(onceName);

            // 备注
            Cell cell4 = row.getCell(4);
            String remark = cell4.getStringCellValue();
            schoolDO.setRemark(remark);

            // 主管部门
            Cell cell5 = row.getCell(5);
            String mainManagerDepartment = cell5.getStringCellValue();
            schoolDO.setMainManagerDepartment(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT.getCode(), mainManagerDepartment));

            // 院校隶属
            Cell cell6 = row.getCell(6);
            String educationalInstitutionsSubjection = cell6.getStringCellValue();
            schoolDO.setEducationalInstitutionsSubjection(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_SUBJECTION.getCode(), educationalInstitutionsSubjection));

            // 学历层次
            Cell cell7 = row.getCell(7);
            String educationLevel = cell7.getStringCellValue();
            schoolDO.setEducationLevel(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_EDUCATION_LEVEL.getCode(), educationLevel));

            // 院校官网链接
            Cell cell8 = row.getCell(8);
            String educationalInstitutionsWebsite = cell8.getStringCellValue();
            schoolDO.setEducationalInstitutionsWebsite(educationalInstitutionsWebsite);

            // 院校属性
            Cell cell9 = row.getCell(9);
            String educationalInstitutionsAttribute = cell9.getStringCellValue();
            schoolDO.setEducationalInstitutionsAttribute(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_ATTRIBUTE.getCode(), educationalInstitutionsAttribute));

            // 基本信息
            Cell cell10 = row.getCell(10);
            String baseInfo = cell10.getStringCellValue();
            schoolDO.setBaseInfo(baseInfo);

            // 院校招办链接
            Cell cell11 = row.getCell(11);
            String educationalInstitutionsRecruitUrl = cell11.getStringCellValue();
            schoolDO.setEducationalInstitutionsRecruitUrl(educationalInstitutionsRecruitUrl);

            // 招生章程链接
            Cell cell12 = row.getCell(12);
            String recruitConstitutionUrl = cell12.getStringCellValue();
            schoolDO.setRecruitConstitutionUrl(recruitConstitutionUrl);

            // 双一流学科
            Cell cell13 = row.getCell(13);
            String doubleFirstClassSubject = cell13.getStringCellValue();
            schoolDO.setDoubleFirstClassSubject(doubleFirstClassSubject);

            // 院校图标
            Cell cell14 = row.getCell(14);
            String educationalInstitutionsIconUrl = cell14.getStringCellValue();
            schoolDO.setEducationalInstitutionsIconUrl(educationalInstitutionsIconUrl);

            // 办学层次
            Cell cell15 = row.getCell(15);
            String schoolRunningLevel = cell15.getStringCellValue();
            schoolDO.setSchoolRunningLevel(DictCodeEnum.getIdByValue(DictCodeEnum.DICT_SCHOOL_RUNNING_LEVEL.getCode(), schoolRunningLevel));

            // 省份
            Cell cell16 = row.getCell(16);
            String province = cell16.getStringCellValue();
            Long provinceId = MyStartupRunner.list.stream().filter(city -> city.getCityName().equals(province)).map(CityDO::getId).findFirst().get();
            schoolDO.setProvinceId(provinceId);

            list.add(schoolDO);
        }

        boolean result = schoolService.saveBatch(list);
        return result ? "success" : "error";
    }

}
