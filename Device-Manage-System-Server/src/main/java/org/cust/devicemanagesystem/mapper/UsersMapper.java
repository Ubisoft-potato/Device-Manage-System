package org.cust.devicemanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.cust.devicemanagesystem.model.Users;

/**
 * Mapper 接口
 *
 * @author Long
 * @since 2020-01-04
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

  @Select(
      "SELECT \n"
          + "   u.id,\n"
          + "  u.username,\n"
          + "  u.work_id,\n"
          + "  u.real_name,\n"
          + "  u.tel_phone,\n"
          + "  u.institute,\n"
          + "  u.create_time \n"
          + "FROM\n"
          + "  users u \n"
          + "  JOIN authorities a \n"
          + "    ON u.id = a.user_id \n"
          + "    AND a.authority = #{authority}")
  IPage<Users> selectUserPage(IPage<Users> page, String authority);
}
