package com.home.examination.controller.app;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.home.examination.entity.domain.MajorDO;
import com.home.examination.entity.domain.SchoolDO;
import com.home.examination.entity.domain.UserDO;
import com.home.examination.entity.domain.VolunteerDO;
import com.home.examination.entity.page.VolunteerPager;
import com.home.examination.entity.vo.ExecuteResult;
import com.home.examination.entity.vo.VolunteerVO;
import com.home.examination.service.MajorService;
import com.home.examination.service.SchoolService;
import com.home.examination.service.VolunteerService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app/volunteer")
public class VolunteerAppController {

    @Resource
    private VolunteerService volunteerService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SchoolService schoolService;
    @Resource
    private MajorService majorService;

    /**
     * 专业主导
     * @return
     */
    @PostMapping("/listVolunteerLeading")
    public ExecuteResult listVolunteerLeading(String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        LambdaQueryWrapper<VolunteerDO> volunteerQueryWrapper = new LambdaQueryWrapper<>();
        volunteerQueryWrapper.eq(VolunteerDO::getUserId, userDO.getId());
        List<VolunteerDO> list = volunteerService.list(volunteerQueryWrapper);

        if(list.isEmpty()) {
            return new ExecuteResult();
        }

        List<Long> schoolIdList = list.stream().map(VolunteerDO::getSchoolId).collect(Collectors.toList());
        Map<Long, List<VolunteerDO>> collect = list.stream().collect(Collectors.groupingBy(VolunteerDO::getSchoolId));

        Map<Long, List<MajorDO>> volunteerList = new HashMap<>();
        for(Map.Entry<Long, List<VolunteerDO>> entry :collect.entrySet()) {
            List<Long> majorIdList = entry.getValue().stream().map(VolunteerDO::getMajorId).collect(Collectors.toList());
            LambdaQueryWrapper<MajorDO> majorQueryWrapper = new LambdaQueryWrapper<>();
            majorQueryWrapper.in(MajorDO::getId, majorIdList);
            List<MajorDO> majorList = majorService.list(majorQueryWrapper);
            volunteerList.put(entry.getKey(), majorList);
        }

        LambdaQueryWrapper<SchoolDO> schoolQueryWrapper = new LambdaQueryWrapper<>();
        schoolQueryWrapper.in(SchoolDO::getId, schoolIdList);
        List<SchoolDO> schoolList = schoolService.list(schoolQueryWrapper);

        schoolList.forEach(schoolDO -> {
            schoolDO.setMajorList(volunteerList.get(schoolDO.getId()));
        });

        VolunteerVO volunteerVO = new VolunteerVO();
        volunteerVO.setSchoolList(schoolList);

        return new ExecuteResult(volunteerVO);
    }

    @PostMapping("/delete")
    public ExecuteResult delete(Long schoolId, Long majorId, String token) {
        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(token);
        LambdaQueryWrapper<VolunteerDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VolunteerDO::getMajorId, majorId).eq(VolunteerDO::getSchoolId, schoolId).eq(VolunteerDO::getUserId, userDO.getId());
        boolean result = volunteerService.remove(wrapper);
        return new ExecuteResult(result);
    }

    @GetMapping("/detail")
    public ExecuteResult detail(Long id) {
        return new ExecuteResult(volunteerService.getById(id));
    }

}
