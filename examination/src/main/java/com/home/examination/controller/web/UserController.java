package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.common.enumerate.DictCodeEnum;
import com.home.examination.common.runner.MyStartupRunner;
import com.home.examination.entity.domain.CityDO;
import com.home.examination.entity.domain.FeatureDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.page.UserPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/web/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping("/listPage")
    @ResponseBody
    public UserPager listPage(UserPager pager) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        userService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<UserDO> listSuggest(UserDO userDO) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(userDO.getTrueName()), " name like '%" + userDO.getTrueName() + "%'");
        List<UserDO> list = userService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = userService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteBatch")
    @ResponseBody
    public Map<String, String> deleteBatch(Long[] ids) {
        boolean result = userService.removeByIds(Arrays.asList(ids));
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String, String> deleteBatch() {
        boolean result = userService.remove(new LambdaQueryWrapper<>());
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        UserDO userDO = new UserDO();
        if (id != null) {
            userDO = userService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/user/modify");
        model.addAttribute("user", userDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(UserDO param) {
        ModelAndView mav = new ModelAndView("/pages/user/list");
        userService.saveOrUpdate(param);
        return mav;
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam String loginName, @RequestParam String password) throws IOException {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getLoginName, loginName).eq(UserDO::getPassword, password);
        UserDO one = userService.getOne(queryWrapper);
        if (one == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            String uuid = UUID.randomUUID().toString();
            Cookie token = new Cookie("token", uuid);
            token.setPath(request.getContextPath());
            token.setMaxAge(60 * 30);
            response.addCookie(token);

            Cookie trueName = new Cookie("trueName", one.getTrueName());
            trueName.setPath(request.getContextPath());
            trueName.setMaxAge(60 * 30);
            response.addCookie(trueName);

            Cookie loginUser = new Cookie("loginName", loginName);
            loginUser.setPath(request.getContextPath());
            loginUser.setMaxAge(60 * 30);
            response.addCookie(loginUser);

            redisTemplate.opsForValue().set(uuid, one, 30, TimeUnit.MINUTES);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
                redisTemplate.delete(cookie.getValue());
            }
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
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
        List<UserDO> list = new ArrayList<>();
        UserDO userDO;
        for (int i = 1; i < lastRowNum; i++) {
            userDO = new UserDO();
            Row row = sheetAt.getRow(i);

            // 登录名
            Cell cell = row.getCell(0);
            String loginName = cell.getStringCellValue();
            userDO.setLoginName(loginName);

            // 密码
            Cell cell1 = row.getCell(1);
            String password = cell1.getStringCellValue();
            userDO.setPassword(password);

            // 真实姓名
            Cell cell2 = row.getCell(2);
            String trueName = cell2.getStringCellValue();
            userDO.setTrueName(trueName);

            // 省份
            Cell cell3 = row.getCell(3);
            if (cell3 != null) {
                String province = cell3.getStringCellValue();
                Long provinceId = MyStartupRunner.list.stream().filter(city -> city.getCityName().equals(province)).map(CityDO::getId).findFirst().orElseGet(() -> MyStartupRunner.list.stream().filter(city -> city.getCityName().indexOf(province) > -1).map(CityDO::getId).findFirst().orElse(null));
                userDO.setProvinceId(provinceId);
            }

            // 民族
            Cell cell4 = row.getCell(4);
            String nation = cell4.getStringCellValue();
            userDO.setNation(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_USER_NATION.getCode(), nation));

            // 高考年份
            Cell cell5 = row.getCell(5);
            if (cell5 != null) {
                String collegeYears = "";
                if(cell5.getCellType().equals(CellType.NUMERIC)) {
                    collegeYears = String.valueOf((int) cell5.getNumericCellValue());
                } else if(cell5.getCellType().equals(CellType.STRING)) {
                    collegeYears = cell5.getStringCellValue();
                }
                userDO.setCollegeYears(collegeYears);
            }

            // 科类
            Cell cell6 = row.getCell(6);
            String subjectType = cell6.getStringCellValue();
            userDO.setSubjectType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_SUBJECT_TYPE.getCode(), subjectType));

            // 类型
            Cell cell7 = row.getCell(7);
            String type = cell7.getStringCellValue();
            userDO.setType(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_USER_TYPE.getCode(), type));

            // 高考分数
            Cell cell8 = row.getCell(8);
            if (cell8 != null) {
                BigDecimal collegeScore = new BigDecimal(cell8.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                userDO.setCollegeScore(collegeScore);
            }

            // 预估分数
            Cell cell9 = row.getCell(9);
            if (cell9 != null) {
                BigDecimal predictedScore = new BigDecimal(cell9.getNumericCellValue()).setScale(2, RoundingMode.HALF_UP);
                userDO.setPredictedScore(predictedScore);
            }

            // 序列号
            Cell cell10 = row.getCell(10);
            String serialNumber = cell10.getStringCellValue();
            userDO.setSerialNumber(serialNumber);

            // 授权码
            Cell cell11 = row.getCell(11);
            String authorizationCode = cell11.getStringCellValue();
            userDO.setAuthorizationCode(authorizationCode);

            // 性别
            Cell cell13 = row.getCell(13);
            String sex = cell13.getStringCellValue();
            userDO.setSex(DictCodeEnum.getNumByValue(DictCodeEnum.DICT_USER_SEX.getCode(), sex));

            list.add(userDO);
        }

        boolean result = userService.saveBatch(list);
        return result ? "success" : "error";
    }

}