package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.SchoolService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
        queryWrapper.orderByAsc(SchoolDO::getEducationalCode);
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

        String[] childrenTypeArray = param.getChildrenTypeArray();
        if (childrenTypeArray != null && childrenTypeArray.length > 0) {
            String collect = Arrays.asList(childrenTypeArray).stream().collect(Collectors.joining(","));
            param.setChildrenType(collect);
        }

        String[] featureEducationalArray = param.getFeatureEducationalArray();
        if (featureEducationalArray != null && featureEducationalArray.length > 0) {
            String collect = Arrays.asList(featureEducationalArray).stream().collect(Collectors.joining(","));
            param.setFeatureEducational(collect);
        }

        String[] educationalInstitutionsAttributeArray = param.getEducationalInstitutionsAttributeArray();
        if (educationalInstitutionsAttributeArray != null && educationalInstitutionsAttributeArray.length > 0) {
            String collect = Arrays.asList(educationalInstitutionsAttributeArray).stream().collect(Collectors.joining(","));
            param.setEducationalInstitutionsAttribute(collect);
        }

        String[] doubleFirstClassSubjectArray = param.getDoubleFirstClassSubjectArray();
        if (doubleFirstClassSubjectArray != null && doubleFirstClassSubjectArray.length > 0) {
            String collect = Arrays.asList(doubleFirstClassSubjectArray).stream().collect(Collectors.joining(","));
            param.setDoubleFirstClassSubject(collect);
        }

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

        int lastRowNum = sheetAt.getLastRowNum() + 1;
        List<SchoolDO> list = new ArrayList<>();
        SchoolDO schoolDO;
        for (int i = 1; i < lastRowNum; i++) {
            schoolDO = new SchoolDO();
            Row row = sheetAt.getRow(i);

            // 院校代码
            Cell cell0 = row.getCell(0);
            String educationalCode = "";
            if (cell0.getCellType().equals(CellType.NUMERIC)) {
                educationalCode = String.valueOf((int) cell0.getNumericCellValue());
            } else if (cell0.getCellType().equals(CellType.STRING)) {
                educationalCode = cell0.getStringCellValue();
            }
            schoolDO.setEducationalCode(educationalCode);

            // 学校名称
            Cell cell1 = row.getCell(1);
            String name = cell1.getStringCellValue();
            if (StringUtils.isEmpty(name)) continue;
            schoolDO.setName(name);

            // 学校主类型
            Cell cell2 = row.getCell(2);
            if (cell2 != null) {
                String mainType = cell2.getStringCellValue();
                String mainTypeValue = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_TYPE.getCode(), mainType);
                schoolDO.setMainType(StringUtils.isEmpty(mainTypeValue) ? mainType : mainTypeValue);
            }

            // 学校子类型
            Cell cell3 = row.getCell(3);
            if (cell3 != null) {
                String childrenType = cell3.getStringCellValue();
                if (!StringUtils.isEmpty(childrenType)) {
                    String[] split = childrenType.split(",");
                    String childrenTypeValue = "";
                    for (String str :
                            split) {
                        if (StringUtils.isEmpty(str)) continue;

                        String assembly = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_TYPE.getCode(), str);
                        if(StringUtils.isEmpty(assembly)) continue;

                        childrenTypeValue += assembly + ",";
                    }
                    schoolDO.setChildrenType(StringUtils.isEmpty(childrenTypeValue) ? childrenType : childrenTypeValue.substring(0, childrenTypeValue.length() - 1));
                }
            }

            // 曾用名
            Cell cell4 = row.getCell(4);
            if (cell4 != null) {
                String onceName = cell4.getStringCellValue();
                schoolDO.setOnceName(onceName);
            }

            // 备注
            Cell cell5 = row.getCell(5);
            if (cell5 != null) {
                String remark = cell5.getStringCellValue();
                schoolDO.setRemark(remark);
            }

            // 主管部门
            Cell cell6 = row.getCell(6);
            if (cell6 != null) {
                String mainManagerDepartment = cell6.getStringCellValue();
                String mainManagerDepartmentValue = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT.getCode(), mainManagerDepartment);
                schoolDO.setMainManagerDepartment(StringUtils.isEmpty(mainManagerDepartmentValue) ? mainManagerDepartment : mainManagerDepartmentValue);
            }

            // 院校属性
            Cell cell7 = row.getCell(7);
            if (cell7 != null) {
                String educationalInstitutionsAttribute = cell7.getStringCellValue();
                if (!StringUtils.isEmpty(educationalInstitutionsAttribute)) {
                    String[] split = educationalInstitutionsAttribute.split(",");
                    String educationalInstitutionsAttributeValue = "";
                    for (String str :
                            split) {
                        if (StringUtils.isEmpty(str)) continue;

                        String assembly = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_EDUCATIONAL_INSTITUTIONS_ATTRIBUTE.getCode(), str);
                        if(StringUtils.isEmpty(assembly)) continue;

                        educationalInstitutionsAttributeValue += assembly + ",";
                    }
                    schoolDO.setEducationalInstitutionsAttribute(StringUtils.isEmpty(educationalInstitutionsAttributeValue) ? educationalInstitutionsAttribute : educationalInstitutionsAttributeValue.substring(0, educationalInstitutionsAttributeValue.length() - 1));
                }
            }

            // 学历层次
            Cell cell8 = row.getCell(8);
            if (cell8 != null) {
                String educationLevel = cell8.getStringCellValue();
                String educationLevelValue = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_EDUCATION_LEVEL.getCode(), educationLevel);
                schoolDO.setEducationLevel(StringUtils.isEmpty(educationLevelValue) ? educationLevel : educationLevelValue);
            }

            // 基本信息
            Cell cell9 = row.getCell(9);
            if (cell9 != null) {
                String baseInfo = cell9.getStringCellValue();
                schoolDO.setBaseInfo(baseInfo);
            }

            // 特色教育
            Cell cell10 = row.getCell(10);
            if (cell10 != null) {
                String featureEducational = cell10.getStringCellValue();
                if (!StringUtils.isEmpty(featureEducational)) {
                    String[] split = featureEducational.split(",");
                    String featureEducationalValue = "";
                    for (String str :
                            split) {
                        if (StringUtils.isEmpty(str)) continue;

                        String assembly = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_FEATURE_EDUCATIONAL.getCode(), str);
                        if(StringUtils.isEmpty(assembly)) continue;
                        featureEducationalValue += assembly + ",";
                    }
                    schoolDO.setFeatureEducational(StringUtils.isEmpty(featureEducationalValue) ? featureEducational : featureEducationalValue.substring(0, featureEducationalValue.length() - 1));
                }
            }

            // 双一流学科
            Cell cell11 = row.getCell(11);
            if (cell11 != null) {
                String doubleFirstClassSubject = cell11.getStringCellValue();
                if (!StringUtils.isEmpty(doubleFirstClassSubject)) {
                    String[] split = doubleFirstClassSubject.split(",");
                    String doubleFirstClassSubjectValue = "";
                    for (String str :
                            split) {
                        if (StringUtils.isEmpty(str)) continue;

                        String assembly = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_DOUBLE_FIRST_CLASS_SUBJECT.getCode(), str);
                        if(StringUtils.isEmpty(assembly)) continue;
                        doubleFirstClassSubjectValue += assembly + ",";
                    }
                    schoolDO.setDoubleFirstClassSubject(StringUtils.isEmpty(doubleFirstClassSubjectValue) ? doubleFirstClassSubject : doubleFirstClassSubjectValue.substring(0, doubleFirstClassSubjectValue.length() - 1));
                }
            }

            // 院校图标
            Cell cell12 = row.getCell(12);
            if (cell12 != null) {
                String educationalInstitutionsIconUrl = cell12.getStringCellValue();
                schoolDO.setEducationalInstitutionsIconUrl(educationalInstitutionsIconUrl);
            }

            // 省份
            Cell cell13 = row.getCell(13);
            if (cell13 != null) {
                String province = cell13.getStringCellValue();
                Long provinceId = MyStartupRunner.list.stream().filter(city -> city.getCityName().equals(province)).map(CityDO::getId).findFirst().orElseGet(() -> MyStartupRunner.list.stream().filter(city -> city.getCityName().indexOf(province) > -1).map(CityDO::getId).findFirst().orElse(null));
                schoolDO.setProvinceId(provinceId);
            }

            // 院校官网链接
            Cell cell14 = row.getCell(14);
            if (cell14 != null) {
                String educationalInstitutionsWebsite = cell14.getStringCellValue();
                schoolDO.setEducationalInstitutionsWebsite(educationalInstitutionsWebsite);
            }

            // 院校招办链接
            Cell cell15 = row.getCell(15);
            if (cell15 != null) {
                String educationalInstitutionsRecruitUrl = cell15.getStringCellValue();
                schoolDO.setEducationalInstitutionsRecruitUrl(educationalInstitutionsRecruitUrl);
            }

            // 招生章程链接
            Cell cell16 = row.getCell(16);
            if (cell16 != null) {
                String recruitConstitutionUrl = cell16.getStringCellValue();
                schoolDO.setRecruitConstitutionUrl(recruitConstitutionUrl);
            }

            // 办学层次
            Cell cell17 = row.getCell(17);
            if (cell17 != null) {
                String schoolRunningLevel = cell17.getStringCellValue();
                String schoolRunningLevelValue = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_RUNNING_LEVEL.getCode(), schoolRunningLevel);
                schoolDO.setSchoolRunningLevel(StringUtils.isEmpty(schoolRunningLevelValue) ? schoolRunningLevel : schoolRunningLevelValue);
            }

            list.add(schoolDO);
        }

        boolean result = schoolService.saveBatch(list);
        return result ? "success" : "error";
    }

}
