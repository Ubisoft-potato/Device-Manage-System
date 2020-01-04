package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.UsersMapper;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IUsersService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Long
 * @since 2020-01-04
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
