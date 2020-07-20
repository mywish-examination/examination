package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.page.HistoryAdmissionDataPager;
import com.home.examination.service.HistoryAdmissionDataService;
import com.home.examination.service.MajorService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/historyAdmissionData")
public class HistoryAdmissionDataController {

    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private MajorService majorService;

    @PostMapping("/listPage")
    @ResponseBody
    public HistoryAdmissionDataPager listPage(HistoryAdmissionDataPager pager) {
        LambdaQueryWrapper<HistoryAdmissionDataDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = historyAdmissionDataService.countByQueryWrapper(queryWrapper);
        List<HistoryAdmissionDataDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = historyAdmissionDataService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = historyAdmissionDataService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = historyAdmissionDataService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = historyAdmissionDataService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        HistoryAdmissionDataDO historyAdmissionDataDO = new HistoryAdmissionDataDO();
        if (id != null) {
            historyAdmissionDataDO = historyAdmissionDataService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/historyAdmissionData/modify");
        model.addAttribute("historyAdmissionData", historyAdmissionDataDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(HistoryAdmissionDataDO param) {
        ModelAndView mav = new ModelAndView("/pages/historyAdmissionData/list");
        historyAdmissionDataService.saveOrUpdate(param);
        return mav;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("Filedata");
        String tempFileName = request.getParameter("Filename");
        String fileExtensionName = tempFileName.substring(tempFileName.lastIndexOf("."));

        ZipSecureFile.setMinInflateRatio(-1.0d);
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
        List<String> majorNameList = new ArrayList<>(lastRowNum);
        List<HistoryAdmissionDataDO> list = new ArrayList<>();
        HistoryAdmissionDataDO historyAdmissionData;
        for (int i = 1; i < lastRowNum; i++) {
            historyAdmissionData = new HistoryAdmissionDataDO();
            Row row = sheetAt.getRow(i);

            // 院校代码
            Cell cell = row.getCell(0);
            if (cell == null) continue;
            String educationalCode = cell.getStringCellValue();
            if (StringUtils.isEmpty(educationalCode)) continue;
            historyAdmissionData.setEducationalCode(educationalCode);

            // 专业名称
            Cell cell1 = row.getCell(1);
            if (cell1 != null) {
                String majorName = cell1.getStringCellValue();
                majorNameList.add(majorName);
                historyAdmissionData.setMajorName(majorName);
            }

            // 年份
            Cell cell2 = row.getCell(2);
            if (cell2 != null) {
                int years = 0;
                if (cell2.getCellType().equals(CellType.NUMERIC)) {
                    years = (int) cell2.getNumericCellValue();
                } else if (cell2.getCellType().equals(CellType.STRING)) {
                    years = Integer.valueOf(cell2.getStringCellValue());
                }
                historyAdmissionData.setYears(years);
            }

            // 最高分
            Cell cell3 = row.getCell(3);
            if (cell3 != null) {
                BigDecimal highestScore = new BigDecimal(cell3.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                historyAdmissionData.setHighestScore(highestScore);
            }

            // 最低分
            Cell cell4 = row.getCell(4);
            if (cell4 != null) {
                BigDecimal minimumScore = new BigDecimal(cell4.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                historyAdmissionData.setMinimumScore(minimumScore);
            }

            // 平均分
            Cell cell5 = row.getCell(5);
            if (cell5 != null) {
                BigDecimal average = new BigDecimal(cell5.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                historyAdmissionData.setAverage(average);
            }

            // 控制线
            Cell cell6 = row.getCell(6);
            if (cell6 != null) {
                BigDecimal controlLine = new BigDecimal(cell6.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                historyAdmissionData.setControlLine(controlLine);
            }

            // 批次代码
            Cell cell7 = row.getCell(7);
            if (cell7 != null) {
                historyAdmissionData.setBatchCode(String.valueOf((int) cell7.getNumericCellValue()));
            }

            // 科类代码
            Cell cell8 = row.getCell(8);
            if (cell8 != null)
                historyAdmissionData.setSubjectCode(String.valueOf((int) cell8.getNumericCellValue()));

            // 最高位次
            Cell cell9 = row.getCell(9);
            if (cell9 != null) {
                int highestRank = 0;
                if(cell9.getCellType().equals(CellType.NUMERIC)) {
                    highestRank = (int) cell9.getNumericCellValue();
                } else if(cell9.getCellType().equals(CellType.STRING)) {
                    highestRank = Integer.valueOf(cell9.getStringCellValue());
                }
                historyAdmissionData.setHighestRank(highestRank);
            }

            // 最低位次
            Cell cell10 = row.getCell(10);
            if (cell10 != null) {
                int minimumRank = 0;
                if(cell10.getCellType().equals(CellType.NUMERIC)) {
                    minimumRank = (int) cell10.getNumericCellValue();
                } else if(cell10.getCellType().equals(CellType.STRING)) {
                    minimumRank = Integer.valueOf(cell10.getStringCellValue());
                }
                historyAdmissionData.setMinimumRank(minimumRank);
            }

            // 平均位次
            Cell cell11 = row.getCell(11);
            if (cell11 != null) {
                int avgRank = 0;
                if(cell11.getCellType().equals(CellType.NUMERIC)) {
                    avgRank = (int) cell11.getNumericCellValue();
                } else if(cell11.getCellType().equals(CellType.STRING)) {
                    avgRank = Integer.valueOf(cell11.getStringCellValue());
                }
                historyAdmissionData.setAvgRank(avgRank);
            }

            // 录取人数
            Cell cell12 = row.getCell(12);
            if (cell12 != null) {
                int enrolment = 0;
                if(cell12.getCellType().equals(CellType.NUMERIC)) {
                    enrolment = (int) cell12.getNumericCellValue();
                } else if(cell12.getCellType().equals(CellType.STRING)) {
                    enrolment = Integer.valueOf(cell12.getStringCellValue());
                }
                historyAdmissionData.setEnrolment(enrolment);
            }

            // 备注
            Cell cell13 = row.getCell(13);
            if (cell13 != null) {
                historyAdmissionData.setRemark(cell13.getStringCellValue());
            }

            list.add(historyAdmissionData);
        }

        LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
        majorQueryWrapper.select(MajorDO::getId, MajorDO::getName).in(MajorDO::getName, majorNameList);
        List<MajorDO> majorList = majorService.list(majorQueryWrapper);

        Map<String, Long> majorMap = majorList.stream().collect(Collectors.toMap(MajorDO::getName, MajorDO::getId));

        for (HistoryAdmissionDataDO historyAdmissionDataDO : list) {
            if (majorMap.containsKey(historyAdmissionDataDO.getMajorName()))
                historyAdmissionDataDO.setMajorId(majorMap.get(historyAdmissionDataDO.getMajorName()));
        }
        boolean result = historyAdmissionDataService.saveBatch(list);
        return result ? "success" : "error";
    }

}
