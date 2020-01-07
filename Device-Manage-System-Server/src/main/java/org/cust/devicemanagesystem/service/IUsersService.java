package org.cust.devicemanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cust.devicemanagesystem.model.Users;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Long
 * @since 2020-01-04
 */
public interface IUsersService extends IService<Users> {

    IPage<Users> getUserPageByAuthority(IPage<Users> page, String authority);
}
