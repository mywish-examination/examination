package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.DataDictionaryPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.DataDictionaryService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/dataDictionary")
public class DataDictionaryController {

    @Resource
    private DataDictionaryService dataDictionaryService;

    @GetMapping("/listByCode")
    @ResponseBody
    public List<DataDictionaryDO> listByCode(String code) {
        LambdaQueryWrapper<DataDictionaryDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(code), DataDictionaryDO::getDictCode, code);
        List<DataDictionaryDO> list = dataDictionaryService.list(queryWrapper);

        return list;
    }

    @PostMapping("/listPage")
    @ResponseBody
    public DataDictionaryPager listPage(DataDictionaryPager pager) {
        LambdaQueryWrapper<DataDictionaryDO> queryWrapper = new LambdaQueryWrapper<>();
        dataDictionaryService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = dataDictionaryService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");

        // 重新初始化数据字典到redis
        MyStartupRunner.map.clear();
        MyStartupRunner.map.putAll(dataDictionaryService.initList());
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        DataDictionaryDO dataDictionaryDO = new DataDictionaryDO();
        if (id != null) {
            dataDictionaryDO = dataDictionaryService.getById(id);
        }

        ModelAndView mav = new ModelAndView("/pages/dataDictionary/modify");
        model.addAttribute("dataDictionary", dataDictionaryDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(DataDictionaryDO param, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/pages/dataDictionary/list");
        dataDictionaryService.saveOrUpdate(param);

        // 重新初始化数据字典到redis
        MyStartupRunner.map.clear();
        MyStartupRunner.map.putAll(dataDictionaryService.initList());

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
        List<DataDictionaryDO> list = new ArrayList<>();
        DataDictionaryDO dataDictionaryDO;
        for (int i = 1; i < lastRowNum; i++) {
            dataDictionaryDO = new DataDictionaryDO();
            Row row = sheetAt.getRow(i);

            // 字典编号
            Cell cell = row.getCell(0);
            String dictCode = cell.getStringCellValue();
            dataDictionaryDO.setDictCode(dictCode);

            // 字典名称
            Cell cell1 = row.getCell(1);
            String dictName = cell1.getStringCellValue();
            dataDictionaryDO.setDictName(dictName);

            // 字典值
            Cell cell2 = row.getCell(2);
            String dictValue = cell2.getStringCellValue();
            dataDictionaryDO.setDictValue(dictValue);

            // 字典数值
            Cell cell3 = row.getCell(3);
            String dictNum = cell3.getStringCellValue();
            dataDictionaryDO.setDictNum(dictNum);

            list.add(dataDictionaryDO);
        }

        boolean result = dataDictionaryService.saveBatch(list);
        return result ? "success" : "error";
    }

}
