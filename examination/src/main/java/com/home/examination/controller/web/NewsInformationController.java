package com.home.examination.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.home.examination.entity.domain.NewsInformationDO;
import com.home.examination.entity.page.NewsInformationPager;
import com.home.examination.service.NewsInformationService;
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
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/web/newsInformation")
public class NewsInformationController {

    @Resource
    private NewsInformationService newsInformationService;
    @Value("${examination.upload.newsInformation-url}")
    private String schoolUrl;

    @PostMapping("/listPage")
    @ResponseBody
    public NewsInformationPager listPage(NewsInformationPager pager) {
        LambdaQueryWrapper<NewsInformationDO> queryWrapper = new LambdaQueryWrapper<>();
        newsInformationService.page(pager.getPager(), queryWrapper);
        return pager;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(Long id) {
        boolean result = newsInformationService.removeById(id);
        Map<String, String> map = new HashMap<>();
        map.put("status", result ? "success" : "error");
        return map;
    }

    @GetMapping("/detail")
    public ModelAndView detail(Long id, Model model) {
        NewsInformationDO newsInformationDO = new NewsInformationDO();
        if (id != null) {
            newsInformationDO = newsInformationService.getById(id);
        }
        ModelAndView mav = new ModelAndView("/pages/newsInformation/modify");
        model.addAttribute("newsInformation", newsInformationDO);
        return mav;
    }

    @PostMapping("/saveOrUpdate")
    public ModelAndView saveOrUpdate(NewsInformationDO param) {
        ModelAndView mav = new ModelAndView("/pages/newsInformation/list");
        newsInformationService.saveOrUpdate(param);
        return mav;
    }

    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile("Filedata");
        String tempFileName = request.getParameter("Filename");
        String fileExtensionName = tempFileName.substring(tempFileName.lastIndexOf("."));
        String path = "";
        String realFileName = UUID.randomUUID().toString() + fileExtensionName;
        path = schoolUrl + realFileName;
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
