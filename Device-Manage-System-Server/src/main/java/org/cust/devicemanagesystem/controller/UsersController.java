package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.cust.devicemanagesystem.exception.ServiceException;
import org.cust.devicemanagesystem.mapstruct.UserConverter;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.model.AuthorityCodeEnum;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.cust.devicemanagesystem.service.IUsersService;
import org.cust.devicemanagesystem.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-04
 */
@SuppressWarnings("DuplicatedCode")
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {


    private IUsersService usersService;

    private IAuthoritiesService authoritiesService;

    private UserConverter userConverter;

    private PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public UserInfo getUserInfo(Authentication authentication) {
        log.info("current username: {}", authentication.getName());
        Users user = usersService.getOne(Wrappers
                .lambdaQuery(new Users())
                .eq(Users::getUsername, authentication.getName()))
                .setPassword(null);
        List<String> authorities = authoritiesService.list(Wrappers
                .lambdaQuery(new Authorities()).eq(Authorities::getUserId, user.getId()))
                .stream()
                .map(Authorities::getAuthority)
                .collect(Collectors.toList());
        return userConverter.toUserInfo(user)
                .setIsSuperAdmin(authorities.contains(AuthorityCodeEnum.SUPER_ADMIN.toString()))
                .setAuthorities(authorities);
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/info/{id}")
    public UserInfo getUserInfoById(@NotBlank @PathVariable String id) {
        return userConverter.toUserInfo(usersService.getOne(Wrappers.lambdaQuery(new Users()).eq(Users::getId, id)));
    }

    /**
     * 注册普通用户
     */
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    public boolean userRegister(@Validated @RequestBody UserInfo userInfo) throws ServiceException {
        Users user = userConverter.toUsers(userInfo);
        if (Objects.nonNull(usersService.getOne(Wrappers.lambdaQuery(new Users()).eq(Users::getUsername, user.getUsername())))) {
            throw new ServiceException().setCode("400").setMessage("用户名已存在");
        }
        usersService.save(user.setPassword(passwordEncoder.encode(user.getPassword())));
        return authoritiesService.saveBatch(userInfo.getAuthorities()
                .stream()
                .map(authority -> new Authorities().setAuthority(authority).setUserId(user.getId()))
                .collect(Collectors.toList()));
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addAdminUser")
    public boolean addAdminUser(@Validated @RequestBody UserInfo userInfo) throws ServiceException {
        Users user = userConverter.toUsers(userInfo);
        if (Objects.nonNull(usersService.getOne(Wrappers.lambdaQuery(new Users()).eq(Users::getUsername, user.getUsername())))) {
            throw new ServiceException().setCode("400").setMessage("用户名已存在");
        }
        usersService.save(user.setPassword(passwordEncoder.encode(user.getPassword())));
        return authoritiesService.saveBatch(userInfo.getAuthorities()
                .stream()
                .map(authority -> new Authorities().setAuthority(authority).setUserId(user.getId()))
                .collect(Collectors.toList()));
    }

    /**
     * 通过id删除
     */
    @Transactional
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@NotBlank @PathVariable String id) {
        if (authoritiesService.remove(Wrappers.lambdaQuery(new Authorities()).eq(Authorities::getUserId, id))) {
            return usersService.removeById(id);
        }
        return false;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public boolean updateById(@Validated @RequestBody Users users) {
        return usersService.updateById(users);
    }


    /**
     * 查询列表
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/list")
    public List<Users> list(Users users) {
        QueryWrapper<Users> wp = new QueryWrapper<>();
        //todo init wp
        return usersService.list(wp);
    }

    /**
     * 分页查询
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/page/{authority}")
    public IPage<UserInfo> page(@NotNull @RequestBody Page<Users> page, @NotBlank @PathVariable String authority) {
        IPage<Users> pageByAuthority = usersService.getUserPageByAuthority(page, authority);
        List<UserInfo> userInfos = pageByAuthority.getRecords().stream()
                .map(userConverter::toUserInfo)
                .collect(Collectors.toList());
        return new Page<UserInfo>()
                .setCurrent(pageByAuthority.getCurrent())
                .setRecords(userInfos)
                .setSize(pageByAuthority.getSize())
                .setTotal(pageByAuthority.getTotal())
                .setPages(pageByAuthority.getPages());
    }


    @Autowired
    public UsersController(IUsersService usersService,
                           IAuthoritiesService authoritiesService,
                           UserConverter userConverter,
                           PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.authoritiesService = authoritiesService;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
    }
}

