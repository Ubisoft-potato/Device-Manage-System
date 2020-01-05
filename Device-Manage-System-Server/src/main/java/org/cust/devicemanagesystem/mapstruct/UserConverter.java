package org.cust.devicemanagesystem.mapstruct;


import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.vo.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    @Mapping(target = "authorities", ignore = true)
    UserInfo toUserInfo(Users user);

    Users toUsers(UserInfo userInfo);
}
