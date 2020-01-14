package com.home.examination.controller.web;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.page.SchoolPager;
import com.home.examination.entity.vo.SuggestVO;
import com.home.examination.service.SchoolService;
import org.springframework.beans.factory.annotation.Value;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/web/school")
public class SchoolController {

    @Resource
    private SchoolService schoolService;
    @Value("${examination.upload.school-url}")
    private String schoolUrl;

    @PostMapping("/listPage")
    @ResponseBody
    public SchoolPager listPage(SchoolPager pager) {
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        schoolService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @GetMapping("/listSuggest")
    @ResponseBody
    public SuggestVO<SchoolDO> listSuggest(SchoolDO schoolDO) {
        LambdaQueryWrapper<SchoolDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply(!StringUtils.isEmpty(schoolDO.getName())," name like '%" + schoolDO.getName() + "%'");
        List<SchoolDO> list = schoolService.list(queryWrapper);

        return new SuggestVO<>(list);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = schoolService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        SchoolDO schoolDO = new SchoolDO();
        if(id != null) {
            schoolDO = schoolService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/school/modify");
        model.addAttribute("school", schoolDO);

        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(SchoolDO param) {
        ModelAndView mav = new ModelAndView("/pages/school/list");
        schoolService.saveOrUpdate(param);
        return mav;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("Filedata");
        String tempFileName = request.getParameter("Filename");
        String fileExtensionName = tempFileName.substring(tempFileName.lastIndexOf("."));
        String realFileName = UUID.randomUUID().toString() + fileExtensionName;
        String path = schoolUrl + realFileName;
        String filePath = request.getServletContext().getRealPath("/") + path;
        try (InputStream is = file.getInputStream();) {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] b = new byte[1024];
            while ((is.read(b)) != -1) {
                fileOutputStream.write(b);
            }

            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
