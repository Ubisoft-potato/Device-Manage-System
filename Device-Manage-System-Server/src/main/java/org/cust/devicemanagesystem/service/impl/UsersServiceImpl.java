package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.UsersMapper;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author Long
 * @since 2020-01-04
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

  private final UsersMapper usersMapper;

  @Override
  public IPage<Users> getUserPageByAuthority(IPage<Users> page, String authority) {
    return usersMapper.selectUserPage(page, authority);
  }

  @Autowired
  public UsersServiceImpl(UsersMapper usersMapper) {
    this.usersMapper = usersMapper;
  }
}
