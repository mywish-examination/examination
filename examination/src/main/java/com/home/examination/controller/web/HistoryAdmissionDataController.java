package com.home.examination.controller.web;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        int count = historyAdmissionDataService.count(queryWrapper);
        if (count < 1) {
            return pager;
        }

        queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
        List<HistoryAdmissionDataDO> list = historyAdmissionDataService.pager(queryWrapper);

        pager.getPager().setTotal(count);
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
        HistoryAdmissionDataDO historyAdmissionDataDO = historyAdmissionDataService.getById(id);
        ModelAndView mav = new ModelAndView("/pages/examinationManager/HistoryAdmissionData/modify");
        model.addAttribute("historyAdmissionData", historyAdmissionDataDO == null ? new HistoryAdmissionDataDO() : historyAdmissionDataDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(HistoryAdmissionDataDO param) {
        ModelAndView mav = new ModelAndView("/pages/examinationManager/historyAdmissionData/list");
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
            if (fileExtensionName.contains(".xls")) {
                book = new HSSFWorkbook(file.getInputStream());
            } else {
                book = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheetAt = book.getSheetAt(0);

        int lastRowNum = sheetAt.getLastRowNum();
        List<String> schoolNameList = new ArrayList<>(lastRowNum);
        List<String> majorNameList = new ArrayList<>(lastRowNum);
        List<HistoryAdmissionDataDO> list = new ArrayList<>();
        HistoryAdmissionDataDO historyAdmissionData;
        for (int i = 1; i < lastRowNum; i++) {
            historyAdmissionData = new HistoryAdmissionDataDO();
            Row row = sheetAt.getRow(i);

            // 学校名称
            Cell cell = row.getCell(0);
            String schoolName = cell.getStringCellValue();
            schoolNameList.add(schoolName);
            historyAdmissionData.setSchoolName(schoolName);

            // 专业名称
            Cell cell1 = row.getCell(1);
            String majorName = cell1.getStringCellValue();
            majorNameList.add(majorName);
            historyAdmissionData.setMajorName(majorName);

            // 年份
            Cell cell2 = row.getCell(2);
            String years = cell2.getStringCellValue();
            historyAdmissionData.setYears(Integer.valueOf(years));

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

            list.add(historyAdmissionData);
        }

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.select(SchoolDO::getId, SchoolDO::getName).in(SchoolDO::getName, schoolNameList);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);

        LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
        majorQueryWrapper.select(MajorDO::getId, MajorDO::getName).in(MajorDO::getName, majorNameList);
        List<MajorDO> majorList = majorService.list(majorQueryWrapper);

        Map<String, Long> schoolMap = schoolList.stream().collect(Collectors.toMap(SchoolDO::getName, SchoolDO::getId));
        Map<String, Long> majorMap = majorList.stream().collect(Collectors.toMap(MajorDO::getName, MajorDO::getId));

        for (HistoryAdmissionDataDO historyAdmissionDataDO : list) {
            if(schoolMap.containsKey(historyAdmissionDataDO.getSchoolName())) schoolMap.get(historyAdmissionDataDO.getSchoolName());
            if(majorMap.containsKey(historyAdmissionDataDO.getMajorName())) majorMap.get(historyAdmissionDataDO.getMajorName());
        }
        boolean result = historyAdmissionDataService.saveBatch(list);
        return result ? "success" : "error";
    }

}