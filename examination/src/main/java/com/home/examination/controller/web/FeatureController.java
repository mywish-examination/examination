package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.entity.domain.DataDictionaryDO;
import com.home.examination.entity.domain.FeatureDO;
import com.home.examination.entity.page.FeaturePager;
import com.home.examination.service.DataDictionaryService;
import com.home.examination.service.FeatureService;
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
@RequestMapping("/web/feature")
public class FeatureController {

    @Resource
    private FeatureService featureService;
    @Resource
    private DataDictionaryService dataDictionaryService;

    @PostMapping("/listPage")
    @ResponseBody
    public FeaturePager listPage(FeaturePager pager) {
        LambdaQueryWrapper<FeatureDO> queryWrapper = new LambdaQueryWrapper<>();
        featureService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = featureService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");

        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        FeatureDO featureDO = new FeatureDO();
        if (id != null) {
            featureDO = featureService.getById(id);

            if (!StringUtils.isEmpty(featureDO.getFeatureCode())) {
                LambdaQueryWrapper<DataDictionaryDO> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(DataDictionaryDO::getDictCode, featureDO.getFeatureCode());

                List<DataDictionaryDO> list = dataDictionaryService.list(queryWrapper);
                model.addAttribute("featureOptionList", list);
            }
        }

        ModelAndView mav = new ModelAndView("/pages/feature/modify");
        model.addAttribute("feature", featureDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(FeatureDO param) {
        ModelAndView mav = new ModelAndView("/pages/feature/list");
        featureService.saveOrUpdate(param);

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
        List<FeatureDO> list = new ArrayList<>();
        FeatureDO featureDO;
        for (int i = 1; i < lastRowNum; i++) {
            featureDO = new FeatureDO();
            Row row = sheetAt.getRow(i);

            // 特征名称
            Cell cell = row.getCell(0);
            String featureName = cell.getStringCellValue();
            featureDO.setFeatureName(featureName);

            // 特征类型
            Cell cell1 = row.getCell(1);
            String featureType = cell1.getStringCellValue();
            featureDO.setFeatureType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_FEATURE_TYPE.getCode(), featureType));

            // 特征选项
            Cell cell2 = row.getCell(2);
            String featureCode = cell2.getStringCellValue();
            featureDO.setFeatureCode(featureCode);

            // 特征选项
            Cell cell3 = row.getCell(3);
            String featureOption = cell3.getStringCellValue();
            featureDO.setFeatureOption(DictCodeEnum.getNumByValue(featureCode, featureOption));

            list.add(featureDO);
        }

        boolean result = featureService.saveBatch(list);
        return result ? "success" : "error";
    }

}
