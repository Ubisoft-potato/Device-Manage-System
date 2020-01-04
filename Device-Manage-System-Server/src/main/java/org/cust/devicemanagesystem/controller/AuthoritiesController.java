package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-04
 */
@RestController
@RequestMapping("/authorities")
public class AuthoritiesController {


    private IAuthoritiesService authoritiesService;


    /**
     * 新增
     */
    @PostMapping
    public boolean save(Authorities authorities) {
        return authoritiesService.save(authorities);
    }

    /**
     * 通过id删除
     */
    @DeleteMapping
    public boolean delete(Authorities authorities) {
        return authoritiesService.removeById(authorities.getId());
    }

    /**
     * 修改
     */
    @PutMapping
    public boolean updateById(Authorities authorities) {
        return authoritiesService.updateById(authorities);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<Authorities> list(Authorities authorities) {
        QueryWrapper<Authorities> wp = new QueryWrapper<>();
        //todo init wp
        return authoritiesService.list(wp);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public IPage<Authorities> page(Page<Authorities> page, Authorities authorities) {
        QueryWrapper<Authorities> wp = new QueryWrapper<>();
        //todo init wp
        return authoritiesService.page(page, wp);
    }


    @Autowired
    public AuthoritiesController(IAuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }
}

