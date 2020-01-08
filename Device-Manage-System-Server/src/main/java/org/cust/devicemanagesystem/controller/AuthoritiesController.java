package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
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
    @PostMapping("/addNewAuthority")
    @ApiOperation("新增用户权限")
    public boolean save(Authorities authorities) {
        return authoritiesService.save(authorities);
    }

    /**
     * 通过id删除
     */
    @DeleteMapping("/deleteAuthority")
    @ApiOperation("删除用户权限")
    public boolean delete(Authorities authorities) {
        return authoritiesService.removeById(authorities.getId());
    }

    /**
     * 修改
     */
    @PutMapping("/updateAuthority")
    @ApiOperation("修改用户权限")
    public boolean updateById(Authorities authorities) {
        return authoritiesService.updateById(authorities);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    @ApiOperation("查询用户权限")
    public List<Authorities> list(Authorities authorities) {
        QueryWrapper<Authorities> wp = new QueryWrapper<>();
        //todo init wp
        return authoritiesService.list(wp);
    }


    @Autowired
    public AuthoritiesController(IAuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }
}

