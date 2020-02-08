package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.HistoryAdmissionDataPager;
import com.home.examination.service.HistoryAdmissionDataService;
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
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/historyAdmissionData")
public class HistoryAdmissionDataController {

    @Resource
    private HistoryAdmissionDataService historyAdmissionDataService;
    @Resource
    private SchoolService schoolService;
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

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        HistoryAdmissionDataDO historyAdmissionDataDO = new HistoryAdmissionDataDO();
        if(id != null) {
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
        List<String> majorNameList = new ArrayList<>(lastRowNum);
        List<HistoryAdmissionDataDO> list = new ArrayList<>();
        HistoryAdmissionDataDO historyAdmissionData;
        for (int i = 1; i < lastRowNum; i++) {
            historyAdmissionData = new HistoryAdmissionDataDO();
            Row row = sheetAt.getRow(i);

            // 院校代码
            Cell cell = row.getCell(0);
            String educationalCode = cell.getStringCellValue();
            if(StringUtils.isEmpty(educationalCode)) continue;
            historyAdmissionData.setEducationalCode(educationalCode);

            // 专业名称
            Cell cell1 = row.getCell(1);
            String majorName = cell1.getStringCellValue();
            majorNameList.add(majorName);
            historyAdmissionData.setMajorName(majorName);

            // 年份
            Cell cell2 = row.getCell(2);
            historyAdmissionData.setYears((int) cell2.getNumericCellValue());

            // 最高分
            Cell cell3 = row.getCell(3);
            BigDecimal highestScore = new BigDecimal(cell3.getNumericCellValue());
            historyAdmissionData.setHighestScore(highestScore);

            // 最低分
            Cell cell4 = row.getCell(4);
            BigDecimal minimumScore = new BigDecimal(cell4.getNumericCellValue());
            historyAdmissionData.setMinimumScore(minimumScore);

            // 平均分
            Cell cell5 = row.getCell(5);
            BigDecimal average = new BigDecimal(cell5.getNumericCellValue());
            historyAdmissionData.setAverage(average);

            // 控制线
            Cell cell6 = row.getCell(6);
            BigDecimal controlLine = new BigDecimal(cell6.getNumericCellValue());
            historyAdmissionData.setControlLine(controlLine);

            // 批次代码
            Cell cell7 = row.getCell(7);
            historyAdmissionData.setBatchCode(cell7.getStringCellValue());
            // 科类代码
            Cell cell8 = row.getCell(8);
            historyAdmissionData.setSubjectCode(cell8.getStringCellValue());
            // 最高位次
            Cell cell9 = row.getCell(9);
            historyAdmissionData.setHighestRank(cell9.getStringCellValue());
            // 最低位次
            Cell cell10 = row.getCell(10);
            historyAdmissionData.setMinimumRank(cell10.getStringCellValue());
            // 平均位次
            Cell cell11 = row.getCell(11);
            historyAdmissionData.setAvgRank(cell11.getStringCellValue());
            // 录取人数
            Cell cell12 = row.getCell(12);
            historyAdmissionData.setEnrolment(cell12.getStringCellValue());
            // 备注
            Cell cell13 = row.getCell(13);
            historyAdmissionData.setRemark(cell13.getStringCellValue());

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
