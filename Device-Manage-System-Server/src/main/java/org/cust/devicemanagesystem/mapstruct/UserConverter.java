package org.cust.devicemanagesystem.mapstruct;

import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.vo.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = String.class)
public interface UserConverter {
  @Mapping(target = "isSuperAdmin", ignore = true)
  @Mapping(target = "authorities", ignore = true)
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "id", expression = "java(String.valueOf(user.getId()))")
  UserInfo toUserInfo(Users user);

  Users toUsers(UserInfo userInfo);
}
