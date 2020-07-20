package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.SubsectionDO;
import com.home.examination.entity.page.SubsectionPager;
import com.home.examination.service.SubsectionService;
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
import java.util.*;

@Controller
@RequestMapping("/web/subsection")
public class SubsectionController {

    @Resource
    private SubsectionService subsectionService;

    @PostMapping("/listPage")
    @ResponseBody
    public SubsectionPager listPage(SubsectionPager pager) {
        LambdaQueryWrapper<SubsectionDO> queryWrapper = new LambdaQueryWrapper<>();
        subsectionService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = subsectionService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = subsectionService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = subsectionService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SubsectionDO subsection = new SubsectionDO();
        if (id != null) {
            subsection = subsectionService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/school/modify");
        model.addAttribute("subsection", subsection);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SubsectionDO param) {
        ModelAndView mav = new ModelAndView("/pages/subsection/list");
        subsectionService.saveOrUpdate(param);
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
        List<SubsectionDO> list = new ArrayList<>();
        SubsectionDO subsectionDO;
        for (int i = 1; i < lastRowNum; i++) {
            subsectionDO = new SubsectionDO();
            Row row = sheetAt.getRow(i);

            // 分数
            Cell cell = row.getCell(0);
            BigDecimal score = new BigDecimal(cell.getNumericCellValue());
            subsectionDO.setScore(score);

            // 人数
            Cell cell0 = row.getCell(1);
            if (cell0 != null) {
                int num = 0;
                if (cell0.getCellType().equals(CellType.NUMERIC)) {
                    num = (int) cell0.getNumericCellValue();
                } else if (cell0.getCellType().equals(CellType.STRING)) {
                    num = Integer.valueOf(cell0.getStringCellValue());
                }
                subsectionDO.setNum(num);
            }


            // 位次
            Cell cell1 = row.getCell(2);
            if (cell1 != null) {
                int rank = 0;
                if (cell1.getCellType().equals(CellType.NUMERIC)) {
                    rank = (int) cell1.getNumericCellValue();
                } else if (cell1.getCellType().equals(CellType.STRING)) {
                    rank = Integer.valueOf(cell1.getStringCellValue());
                }
                subsectionDO.setRank(rank);
            }

            // 年份
            Cell cell2 = row.getCell(3);
            if (cell2 != null) {
                int year = 0;
                if (cell2.getCellType().equals(CellType.NUMERIC)) {
                    year = (int) cell2.getNumericCellValue();
                } else if (cell2.getCellType().equals(CellType.STRING)) {
                    year = Integer.valueOf(cell2.getStringCellValue());
                }
                subsectionDO.setYear(year);
            }

            // 科类
            Cell cell3 = row.getCell(4);
            if (cell3 != null) {
                String subjectType = "";
                if (cell3.getCellType().equals(CellType.NUMERIC)) {
                    subjectType = String.valueOf((int) cell3.getNumericCellValue());
                } else if (cell3.getCellType().equals(CellType.STRING)) {
                    subjectType = cell3.getStringCellValue();
                }
                subsectionDO.setSubjectType(subjectType);
            }

//            // 省份
//            Cell cell16 = row.getCell(5);
//            String province = cell16.getStringCellValue();
//            Long provinceId = MyStartupRunner.list.stream().filter(city -> city.getCityName().equals(province)).map(CityDO::getId).findFirst().get();
//            subsectionDO.setProvinceId(provinceId);

            list.add(subsectionDO);
        }

        boolean result = subsectionService.saveBatch(list);
        return result ? "success" : "error";
    }

}
