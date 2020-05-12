package org.cust.devicemanagesystem.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.cust.devicemanagesystem.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** create by: long description: create time: 2020/1/5 下午9:45 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private final IUsersService usersService;

  private final IAuthoritiesService authoritiesService;

  @Override
  public UserDetails loadUserByUsername(@NotBlank String username)
      throws UsernameNotFoundException {
    // 查询用户
    Users user =
        usersService.getOne(Wrappers.lambdaQuery(new Users()).eq(Users::getUsername, username));
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("用户不存在!");
    }
    // 获取用户权限码
    Set<SimpleGrantedAuthority> authorityList =
        authoritiesService
            .list(Wrappers.lambdaQuery(new Authorities()).eq(Authorities::getUserId, user.getId()))
            .stream()
            .map(authorities -> new SimpleGrantedAuthority(authorities.getAuthority()))
            .collect(Collectors.toSet());
    return new User(user.getUsername(), user.getPassword(), authorityList);
  }

  @Autowired
  public UserDetailServiceImpl(IUsersService usersService, IAuthoritiesService authoritiesService) {
    this.usersService = usersService;
    this.authoritiesService = authoritiesService;
  }
}
