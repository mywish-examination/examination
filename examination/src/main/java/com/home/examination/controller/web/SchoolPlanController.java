package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.HistoryAdmissionDataDO;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolPlanDO;
import com.home.examination.entity.page.HistoryAdmissionDataPager;
import com.home.examination.entity.page.SchoolPlanPager;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolPlanService;
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
@RequestMapping("/web/schoolPlan")
public class SchoolPlanController {

    @Resource
    private SchoolPlanService schoolPlanService;
    @Resource
    private MajorService majorService;

    @PostMapping("/listPage")
    @ResponseBody
    public SchoolPlanPager listPage(SchoolPlanPager pager) {
        LambdaQueryWrapper<SchoolPlanDO> queryWrapper = new LambdaQueryWrapper<>();
        int total = schoolPlanService.countByQueryWrapper(queryWrapper);
        List<SchoolPlanDO> list = Collections.emptyList();
        if (total > 0) {
            queryWrapper.last(String.format("limit %s, %s", pager.getPager().offset(), pager.getPager().getSize()));
            list = schoolPlanService.pageByQueryWrapper(queryWrapper);
        }

        pager.getPager().setTotal(total);
        pager.getPager().setRecords(list);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolPlanService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = schoolPlanService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = schoolPlanService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SchoolPlanDO schoolPlanDO = new SchoolPlanDO();
        if (id != null) {
            schoolPlanDO = schoolPlanService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/schoolPlan/modify");
        model.addAttribute("schoolPlan", schoolPlanDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolPlanDO param) {
        ModelAndView mav = new ModelAndView("/pages/schoolPlan/list");
        schoolPlanService.saveOrUpdate(param);
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
        List<SchoolPlanDO> list = new ArrayList<>();
        SchoolPlanDO schoolPlanDO;
        for (int i = 1; i < lastRowNum; i++) {
            schoolPlanDO = new SchoolPlanDO();
            Row row = sheetAt.getRow(i);

            // 院校代码
            Cell cell = row.getCell(0);
            if (cell == null) continue;
            String educationalCode = cell.getStringCellValue();
            if (StringUtils.isEmpty(educationalCode)) continue;
            schoolPlanDO.setEducationalCode(educationalCode);

            // 院校名称
            Cell cell0 = row.getCell(1);
            if (cell0 != null) {
                String schoolName = cell0.getStringCellValue();
                schoolPlanDO.setSchoolName(schoolName);
            }

            // 专业代号
            Cell cell1 = row.getCell(2);
            if (cell1 != null) {
                String majorCode = "";
                if(cell1.getCellType().equals(CellType.NUMERIC)) {
                    majorCode = String.valueOf(cell1.getNumericCellValue());
                } else if(cell1.getCellType().equals(CellType.STRING)) {
                    majorCode = cell1.getStringCellValue();
                }
                schoolPlanDO.setMajorCode(majorCode);
            }

            // 专业名称
            Cell cell2 = row.getCell(3);
            if (cell2 != null) {
                String majorName = cell2.getStringCellValue();
                schoolPlanDO.setMajorName(majorName);
            }

            // 批次代码
            Cell cell3 = row.getCell(4);
            if (cell3 != null){
                String batchCode = "";
                if(cell3.getCellType().equals(CellType.NUMERIC)) {
                    batchCode = String.valueOf(cell3.getNumericCellValue());
                } else if(cell3.getCellType().equals(CellType.STRING)) {
                    batchCode = cell3.getStringCellValue();
                }
                schoolPlanDO.setBatchCode(batchCode);
            }

            // 科类代码
            Cell cell4 = row.getCell(5);
            if (cell4 != null) {
                String subjecyCode = "";
                if(cell4.getCellType().equals(CellType.NUMERIC)) {
                    subjecyCode = String.valueOf(cell4.getNumericCellValue());
                } else if(cell4.getCellType().equals(CellType.STRING)) {
                    subjecyCode = cell4.getStringCellValue();
                }
                schoolPlanDO.setSubjectCode(subjecyCode);
            }


            // 学制
            Cell cell5 = row.getCell(6);
            if (cell5 != null)
                schoolPlanDO.setEducationalSystem(String.valueOf(cell5.getStringCellValue()));

            // 计划数
            Cell cell6 = row.getCell(7);
            if (cell6 != null) {
                BigDecimal planNum = new BigDecimal(0);
                if(cell6.getCellType().equals(CellType.NUMERIC)) {
                    planNum = new BigDecimal(cell6.getNumericCellValue());
                } else if(cell6.getCellType().equals(CellType.STRING)) {
                    planNum = new BigDecimal(cell6.getStringCellValue());
                }
                schoolPlanDO.setPlanNum(planNum);
            }

            // 收费标准
            Cell cell7 = row.getCell(8);
            if (cell7 != null) {
                String chargingStandard = "";
                if(cell7.getCellType().equals(CellType.NUMERIC)) {
                    chargingStandard = String.valueOf(cell7.getNumericCellValue());
                } else if(cell7.getCellType().equals(CellType.STRING)) {
                    chargingStandard = cell7.getStringCellValue();
                }
                schoolPlanDO.setChargingStandard(chargingStandard);
            }

            // 院校地址
            Cell cell8 = row.getCell(9);
            if (cell8 != null)
                schoolPlanDO.setSchoolAddress(cell8.getStringCellValue());

            // 备注
            Cell cell9 = row.getCell(10);
            if (cell9 != null)
                schoolPlanDO.setRemark(cell9.getStringCellValue());

            // 省份
            Cell cell10 = row.getCell(11);
            if (cell10 != null) {
                String province = cell10.getStringCellValue();
                Long provinceId = MyStartupRunner.list.stream().filter(city -> city.getCityName().equals(province)).map(CityDO::getId).findFirst().orElseGet(() -> MyStartupRunner.list.stream().filter(city -> city.getCityName().indexOf(province) > -1).map(CityDO::getId).findFirst().orElse(null));
                schoolPlanDO.setProvinceId(provinceId);
            }

            // 主管部门
            Cell cell11 = row.getCell(12);
            if (cell11 != null) {
                String mainManagerDepartment = cell11.getStringCellValue();
                String mainManagerDepartmentValue = DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SCHOOL_MAIN_MANAGER_DEPARTMENT.getCode(), mainManagerDepartment);
                schoolPlanDO.setMainManagerDepartment(StringUtils.isEmpty(mainManagerDepartmentValue) ? mainManagerDepartment : mainManagerDepartmentValue);
            }

            list.add(schoolPlanDO);
        }

        boolean result = schoolPlanService.saveBatch(list);
        return result ? "success" : "error";
    }

}
