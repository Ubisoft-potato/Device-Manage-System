package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.cust.devicemanagesystem.exception.ServiceException;
import org.cust.devicemanagesystem.mapstruct.UserConverter;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.model.AuthorityCodeEnum;
import org.cust.devicemanagesystem.model.PasswordReset;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.cust.devicemanagesystem.service.IUsersService;
import org.cust.devicemanagesystem.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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


    private final IUsersService usersService;

    private final IAuthoritiesService authoritiesService;

    private final UserConverter userConverter;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    @ApiOperation(value = "获取用户信息")
    public UserInfo getUserInfo(Authentication authentication) {
        log.info("current username: {}", authentication.getName());
        Users user = usersService.getOne(Wrappers
                .lambdaQuery(Users.class)
                .eq(Users::getUsername, authentication.getName()));
        List<String> authorities = authoritiesService.list(Wrappers
                .lambdaQuery(Authorities.class).eq(Authorities::getUserId, user.getId()))
                .stream()
                .map(Authorities::getAuthority)
                .collect(Collectors.toList());
        return userConverter.toUserInfo(user)
                .setIsSuperAdmin(authorities.contains(AuthorityCodeEnum.SUPER_ADMIN.toString()))
                .setAuthorities(authorities);
    }


    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping("/info/{id}")
    @ApiOperation(value = "管理员通过用户id获取用户信息")
    public UserInfo getUserInfoById(@NotBlank @PathVariable String id) {
        return userConverter.toUserInfo(usersService.getOne(Wrappers.lambdaQuery(new Users()).eq(Users::getId, id)));
    }

    /**
     * 注册普通用户
     */
    @PostMapping("/register")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation(value = "用户注册")
    public boolean userRegister(@Validated @RequestBody UserInfo userInfo) throws ServiceException {
        Users user = userConverter.toUsers(userInfo);
        if (Objects.nonNull(usersService.getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getUsername, user.getUsername())))) {
            throw new ServiceException().setCode("400").setMessage("用户名已存在");
        }
        usersService.save(user.setPassword(passwordEncoder.encode(user.getPassword())));
        return authoritiesService.saveBatch(userInfo.getAuthorities()
                .stream()
                .map(authority -> new Authorities().setAuthority(authority).setUserId(user.getId()))
                .collect(Collectors.toList()));
    }

    @Transactional
    @PutMapping("/changePassword")
    public Boolean changePassword(@RequestBody @Validated PasswordReset passwordReset) throws ServiceException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetail = userDetailsService.loadUserByUsername(userName);
        if (!passwordEncoder.matches(passwordReset.getOldPassword(), userDetail.getPassword())) {
            throw new ServiceException().setCode("403").setMessage("原密码输入错误");
        }
        return usersService.update(Wrappers.lambdaUpdate(Users.class)
                .set(Users::getPassword, passwordEncoder.encode(passwordReset.getNewPassword()))
                .eq(Users::getUsername, userName));
    }


    /**
     * 添加管理员
     *
     * @param userInfo
     * @return
     * @throws ServiceException
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/addAdminUser")
    @ApiOperation(value = "添加管理员")
    public boolean addAdminUser(@Validated @RequestBody UserInfo userInfo) throws ServiceException {
        Users user = userConverter.toUsers(userInfo);
        if (Objects.nonNull(usersService.getOne(Wrappers.lambdaQuery(Users.class).eq(Users::getUsername, user.getUsername())))) {
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
    @ApiOperation(value = "删除用户")
    public boolean delete(@NotBlank @PathVariable String id) {
        if (authoritiesService.remove(Wrappers.lambdaQuery(Authorities.class).eq(Authorities::getUserId, id))) {
            return usersService.removeById(id);
        }
        return false;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新用户信息")
    public boolean updateById(@Validated @RequestBody Users users) {
        return usersService.updateById(users);
    }


    /**
     * 查询列表
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
    @GetMapping("/list/{institute}")
    @ApiOperation("根据用户所在机构查询用户列表")
    public List<UserInfo> list(@NotBlank @PathVariable String institute) {
        return usersService.list(Wrappers.lambdaQuery(Users.class).eq(Users::getInstitute, institute))
                .stream()
                .map(userConverter::toUserInfo)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER')")
    @GetMapping("/nameList/{firstName}")
    @ApiOperation("查询用户姓名列表")
    public List<String> getNameList(@PathVariable String firstName) {
        if (Objects.equals(firstName, "all")) {
            return usersService.list()
                    .stream()
                    .map(Users::getRealName)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return usersService.list(Wrappers.lambdaQuery(Users.class).like(Users::getRealName, firstName))
                .stream()
                .map(Users::getRealName)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 分页查询
     */
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/page/{authority}")
    @ApiOperation(value = "分页查询所有用户")
    public IPage<UserInfo> page(@NotNull @RequestBody Page<Users> page, @NotBlank @PathVariable String authority) {
        IPage<Users> pageByAuthority = usersService.getUserPageByAuthority(page, authority);
        return new Page<UserInfo>()
                .setCurrent(pageByAuthority.getCurrent())
                .setSize(pageByAuthority.getSize())
                .setTotal(pageByAuthority.getTotal())
                .setPages(pageByAuthority.getPages())
                .setRecords(pageByAuthority.getRecords().stream()
                        .map(userConverter::toUserInfo)
                        .collect(Collectors.toList()));
    }


    @Autowired
    public UsersController(IUsersService usersService,
                           IAuthoritiesService authoritiesService,
                           UserConverter userConverter,
                           @Qualifier("userDetailServiceImpl") UserDetailsService userDetailsService,
                           PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.authoritiesService = authoritiesService;
        this.userConverter = userConverter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
}

