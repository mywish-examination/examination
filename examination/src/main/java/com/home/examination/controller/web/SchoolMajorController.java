package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.entity.domain.FeatureDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolMajorDO;
import com.home.examination.entity.page.SchoolMajorPager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolMajorService;
import com.home.examination.service.SchoolService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
@RequestMapping("/web/schoolMajor")
public class SchoolMajorController {

    @Resource
    private SchoolMajorService schoolMajorService;
    @Resource
    private MajorService majorService;
    @Resource
    private SchoolService schoolService;

    @RequestMapping("/listPage")
    @ResponseBody
    public SchoolMajorPager listPage(SchoolMajorPager pager) {
        LambdaQueryWrapper<SchoolMajorDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = schoolMajorService.countByQueryWrapper(queryWrapper);
        List<SchoolMajorDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = schoolMajorService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolMajorService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = schoolMajorService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = schoolMajorService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(SchoolMajorDO major, Model model) {
        SchoolMajorDO schoolMajorDO = new SchoolMajorDO();
        if (major.getId() != null) {
            schoolMajorDO = schoolMajorService.getById(major.getId());

            MajorDO majorDO = majorService.getById(schoolMajorDO.getMajorId());
            schoolMajorDO.setMajorName(majorDO.getName());
        }
        ModelAndView mav = new ModelAndView("/pages/schoolMajor/modify");
        model.addAttribute("schoolMajor", schoolMajorDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolMajorDO param) {
        ModelAndView mav = new ModelAndView("/pages/schoolMajor/list");
        schoolMajorService.saveOrUpdate(param);
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
        List<SchoolMajorDO> list = new ArrayList<>();
        SchoolMajorDO schoolMajorDO;
        for (int i = 1; i < lastRowNum; i++) {
            schoolMajorDO = new SchoolMajorDO();
            Row row = sheetAt.getRow(i);

            // 院校代码
            Cell cell = row.getCell(0);
            String educationalCode = cell.getStringCellValue();
            schoolMajorDO.setEducationalCode(educationalCode);

            // 专业名称
            Cell cell1 = row.getCell(1);
            String majorName = cell1.getStringCellValue();
            schoolMajorDO.setMajorName(majorName);

            // 录取分数线
            Cell cell2 = row.getCell(2);
            if (cell2 != null) {
                BigDecimal admissionScoreLine = new BigDecimal(cell2.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                schoolMajorDO.setAdmissionScoreLine(admissionScoreLine);
            }

            // 招生人数
            Cell cell3 = row.getCell(3);
            if (cell3 != null) {
                int recruitNum = 0;
                if(cell3.getCellType().equals(CellType.NUMERIC)) {
                    recruitNum = (int) cell3.getNumericCellValue();
                } else if(cell3.getCellType().equals(CellType.STRING)) {
                    recruitNum = Integer.valueOf(cell3.getStringCellValue());
                }
                schoolMajorDO.setRecruitNum(recruitNum);
            }

            list.add(schoolMajorDO);
        }

        boolean result = schoolMajorService.saveBatch(list);
        return result ? "success" : "error";
    }

}
