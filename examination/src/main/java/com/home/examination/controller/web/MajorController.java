package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.page.MajorPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/web/major")
public class MajorController {

    @Resource
    private MajorService majorService;
    @Resource
    private SchoolService schoolService;

    @RequestMapping("/listPage")
    @ResponseBody
    public MajorPager listPage(MajorPager pager) {
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();
        majorService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<MajorDO> listSuggest(MajorDO majorDO) {
        LambdaQueryWrapper<MajorDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(majorDO.getName()), " name like '%" + majorDO.getName() + "%'");
        List<MajorDO> list = majorService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = majorService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = majorService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = majorService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(MajorDO major, Model model) {
        MajorDO majorDO = new MajorDO();
        if (major.getId() != null) {
            majorDO = majorService.getById(major.getId());
        }
        ModelAndView mav = new ModelAndView("/pages/major/modify");
        model.addAttribute("major", majorDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(MajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/major/list");
        majorService.saveOrUpdate(param);
        return mav;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
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
        List<MajorDO> list = new ArrayList<>();
        MajorDO major;
        for (int i = 1; i < lastRowNum; i++) {
            major = new MajorDO();
            Row row = sheetAt.getRow(i);

            // 专业名称
            Cell cell = row.getCell(0);
            String name = cell.getStringCellValue();
            major.setName(name);

            // 学科
            Cell cell1 = row.getCell(1);
            String subjectType = cell1.getStringCellValue();
            major.setSubjectType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SUBJECT_TYPE.getCode(), subjectType));

            // 门类
            Cell cell2 = row.getCell(2);
            String categoryType = cell2.getStringCellValue();
            major.setCategoryType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_MAJOR_CATEGORY_TYPE.getCode(), categoryType));

            // 专业类
            Cell cell3 = row.getCell(3);
            String majorType = cell3.getStringCellValue();
            major.setMajorType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_MAJOR_MAJOR_TYPE.getCode(), majorType));

            // 学历
            Cell cell4 = row.getCell(4);
            String education = cell4.getStringCellValue();
            major.setEducation(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_MAJOR_EDUCATION.getCode(), education));

            // 学位
            Cell cell5 = row.getCell(5);
            String academicDegree = cell5.getStringCellValue();
            major.setAcademicDegree(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_MAJOR_ACADEMIC_DEGREE.getCode(), academicDegree));

            // 就业率
            Cell cell6 = row.getCell(6);
            String employmentRate = cell6.getStringCellValue();
            major.setEmploymentRate(employmentRate);

            // 年限
            Cell cell7 = row.getCell(7);
            String years = cell7.getStringCellValue();
            major.setYears(years);

            // 专业介绍
            Cell cell8 = row.getCell(8);
            String majorIntroduce = cell8.getStringCellValue();
            major.setMajorIntroduce(majorIntroduce);

            // 主要课程
            Cell cell9 = row.getCell(9);
            String mainCourse = cell9.getStringCellValue();
            major.setMainCourse(mainCourse);

            // 就业方向
            Cell cell10 = row.getCell(10);
            String employmentDirection = cell10.getStringCellValue();
            major.setEmploymentDirection(employmentDirection);

            // 向阳指导
            Cell cell11 = row.getCell(11);
            String toSunGuidance = cell11.getStringCellValue();
            major.setToSunGuidance(toSunGuidance);

            list.add(major);
        }

        boolean result = majorService.saveBatch(list);
        return result ? "success" : "error";
    }


}
