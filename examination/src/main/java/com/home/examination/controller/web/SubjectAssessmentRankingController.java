package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.SubjectAssessmentRankingDO;
import com.home.examination.entity.page.SubjectAssessmentRankingPager;
import com.home.examination.service.SubjectAssessmentRankingService;
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
@RequestMapping("/web/subjectAssessmentRanking")
public class SubjectAssessmentRankingController {

    @Resource
    private SubjectAssessmentRankingService subjectAssessmentRankingService;

    @PostMapping("/listPage")
    @ResponseBody
    public SubjectAssessmentRankingPager listPage(SubjectAssessmentRankingPager pager) {
        LambdaQueryWrapper<SubjectAssessmentRankingDO> queryWrapper = new LambdaQueryWrapper<>();
        subjectAssessmentRankingService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = subjectAssessmentRankingService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = subjectAssessmentRankingService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = subjectAssessmentRankingService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SubjectAssessmentRankingDO subjectAssessmentRankingDO = new SubjectAssessmentRankingDO();
        if (id != null) {
            subjectAssessmentRankingDO = subjectAssessmentRankingService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/school/modify");
        model.addAttribute("subjectAssessmentRanking", subjectAssessmentRankingDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SubjectAssessmentRankingDO param) {
        ModelAndView mav = new ModelAndView("/pages/subjectAssessmentRanking/list");
        subjectAssessmentRankingService.saveOrUpdate(param);
        return mav;
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
        List<SubjectAssessmentRankingDO> list = new ArrayList<>();
        SubjectAssessmentRankingDO subjectAssessmentRankingDO;
        for (int i = 1; i < lastRowNum; i++) {
            subjectAssessmentRankingDO = new SubjectAssessmentRankingDO();
            Row row = sheetAt.getRow(i);

            // id
            Cell cell = row.getCell(0);
            Long id = Long.valueOf((int) cell.getNumericCellValue());
            subjectAssessmentRankingDO.setId(id);

            // 学科门类
            Cell cell0 = row.getCell(1);
            String categoryName = cell0.getStringCellValue();
            subjectAssessmentRankingDO.setCategoryName(categoryName);

            // 学科代码
            Cell cell1 = row.getCell(2);
            String subjectType = cell1.getStringCellValue();
            subjectAssessmentRankingDO.setSubjectType(subjectType);

            // 学科门类代码
            Cell cell2 = row.getCell(3);
            String categoryType = cell2.getStringCellValue();
            subjectAssessmentRankingDO.setCategoryType(categoryType);

            // 学科名称
            Cell cell3 = row.getCell(4);
            String subjectName = cell3.getStringCellValue();
            subjectAssessmentRankingDO.setSubjectName(subjectName);

            // 等级
            Cell cell4 = row.getCell(5);
            String level = cell4.getStringCellValue();
            subjectAssessmentRankingDO.setLevel(level);

            // 院校名称
            Cell cell5 = row.getCell(6);
            String schoolName = cell5.getStringCellValue();
            subjectAssessmentRankingDO.setSchoolName(schoolName);

            // 全国排名
            Cell cell6 = row.getCell(7);
            String nationalRankings = cell6.getStringCellValue();
            subjectAssessmentRankingDO.setNationalRankings(nationalRankings);

            // 档次
            Cell cell7 = row.getCell(8);
            String grade = cell7.getStringCellValue();
            subjectAssessmentRankingDO.setGrade(grade);

            subjectAssessmentRankingDO.setEducationalCode(String.valueOf(i));

            list.add(subjectAssessmentRankingDO);
        }

        boolean result = subjectAssessmentRankingService.saveBatch(list);
        return result ? "success" : "error";
    }

}
