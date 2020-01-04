package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IUsersService;
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
@RequestMapping("/users")
public class UsersController {


    private IUsersService usersService;


    /**
     * 新增
     */
    @PostMapping
    public boolean save(Users users) {
        return usersService.save(users);
    }

    /**
     * 通过id删除
     */
    @DeleteMapping
    public boolean delete(Users users) {
        return usersService.removeById(users.getId());
    }

    /**
     * 修改
     */
    @PutMapping
    public boolean updateById(Users users) {
        return usersService.updateById(users);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<Users> list(Users users) {
        QueryWrapper<Users> wp = new QueryWrapper<>();
        //todo init wp
        return usersService.list(wp);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public IPage<Users> page(Page<Users> page, Users users) {
        QueryWrapper<Users> wp = new QueryWrapper<>();
        //todo init wp
        return usersService.page(page, wp);
    }


    @Autowired
    public UsersController(IUsersService usersService) {
        this.usersService = usersService;
    }
}

