package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.HistoryDataControlLineDO;
import com.home.examination.entity.page.HistoryDataControlLinePager;
import com.home.examination.service.DataDictionaryService;
import com.home.examination.service.HistoryDataControlLineService;
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
import java.util.*;

@Controller
@RequestMapping("/web/historyDataControlLine")
public class HistoryDataControlLineController {

    @Resource
    private HistoryDataControlLineService historyDataControlLineService;

    @PostMapping("/listPage")
    @ResponseBody
    public HistoryDataControlLinePager listPage(HistoryDataControlLinePager pager) {
        LambdaQueryWrapper<HistoryDataControlLineDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(HistoryDataControlLineDO::getYears);
        historyDataControlLineService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = historyDataControlLineService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");

        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = historyDataControlLineService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = historyDataControlLineService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        HistoryDataControlLineDO historyDataControlLineDO = new HistoryDataControlLineDO();
        if (id != null) {
            historyDataControlLineDO = historyDataControlLineService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/historyDataControlLine/modify");
        model.addAttribute("historyDataControlLine", historyDataControlLineDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(HistoryDataControlLineDO param) {
        ModelAndView mav = new ModelAndView("/pages/feature/list");
        historyDataControlLineService.saveOrUpdate(param);

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
        List<HistoryDataControlLineDO> list = new ArrayList<>();
        HistoryDataControlLineDO historyDataControlLineDO;
        for (int i = 1; i < lastRowNum; i++) {
            historyDataControlLineDO = new HistoryDataControlLineDO();
            Row row = sheetAt.getRow(i);

            // 年份
            Cell cell = row.getCell(0);
            if (cell != null) {
                String years = "";
                if (cell.getCellType().equals(CellType.NUMERIC)) {
                    years = String.valueOf(cell.getNumericCellValue());
                } else if (cell.getCellType().equals(CellType.STRING)) {
                    years = cell.getStringCellValue();
                }
                historyDataControlLineDO.setYears(years);
            }


            // 文科-重点线
            Cell cell1 = row.getCell(1);
            if (cell1 != null) {
                String scienceKeyLine = "";
                if (cell1.getCellType().equals(CellType.NUMERIC)) {
                    scienceKeyLine = String.valueOf(cell1.getNumericCellValue());
                } else if (cell1.getCellType().equals(CellType.STRING)) {
                    scienceKeyLine = cell1.getStringCellValue();
                }
                historyDataControlLineDO.setScienceKeyLine(scienceKeyLine);
            }

            // 文科-本科线
            Cell cell2 = row.getCell(2);
            if (cell2 != null) {
                String scienceUndergraduateLine = "";
                if (cell2.getCellType().equals(CellType.NUMERIC)) {
                    scienceUndergraduateLine = String.valueOf(cell2.getNumericCellValue());
                } else if (cell2.getCellType().equals(CellType.STRING)) {
                    scienceUndergraduateLine = cell2.getStringCellValue();
                }
                historyDataControlLineDO.setScienceUndergraduateLine(scienceUndergraduateLine);
            }

            // 文科-专科线
            Cell cell3 = row.getCell(3);
            if (cell3 != null) {
                String scienceSpecialtyLine = "";
                if (cell3.getCellType().equals(CellType.NUMERIC)) {
                    scienceSpecialtyLine = String.valueOf(cell3.getNumericCellValue());
                } else if (cell3.getCellType().equals(CellType.STRING)) {
                    scienceSpecialtyLine = cell3.getStringCellValue();
                }
                historyDataControlLineDO.setScienceSpecialtyLine(scienceSpecialtyLine);
            }

            // 理科-重点线
            Cell cell4 = row.getCell(4);
            if (cell4 != null) {
                String liberalArtsKeyLine = "";
                if (cell4.getCellType().equals(CellType.NUMERIC)) {
                    liberalArtsKeyLine = String.valueOf(cell4.getNumericCellValue());
                } else if (cell4.getCellType().equals(CellType.STRING)) {
                    liberalArtsKeyLine = cell.getStringCellValue();
                }
                historyDataControlLineDO.setLiberalArtsKeyLine(liberalArtsKeyLine);
            }

            // 理科-本科线
            Cell cell5 = row.getCell(5);
            if (cell5 != null) {
                String liberalArtsUndergraduateLine = "";
                if (cell5.getCellType().equals(CellType.NUMERIC)) {
                    liberalArtsUndergraduateLine = String.valueOf(cell5.getNumericCellValue());
                } else if (cell5.getCellType().equals(CellType.STRING)) {
                    liberalArtsUndergraduateLine = cell5.getStringCellValue();
                }
                historyDataControlLineDO.setLiberalArtsUndergraduateLine(liberalArtsUndergraduateLine);
            }

            // 理科-专科线
            Cell cell6 = row.getCell(6);
            if (cell6 != null) {
                String liberalArtsSpecialtyLine = "";
                if (cell6.getCellType().equals(CellType.NUMERIC)) {
                    liberalArtsSpecialtyLine = String.valueOf(cell6.getNumericCellValue());
                } else if (cell6.getCellType().equals(CellType.STRING)) {
                    liberalArtsSpecialtyLine = cell6.getStringCellValue();
                }
                historyDataControlLineDO.setLiberalArtsSpecialtyLine(liberalArtsSpecialtyLine);
            }

            list.add(historyDataControlLineDO);
        }

        boolean result = historyDataControlLineService.saveBatch(list);
        return result ? "success" : "error";
    }

}
