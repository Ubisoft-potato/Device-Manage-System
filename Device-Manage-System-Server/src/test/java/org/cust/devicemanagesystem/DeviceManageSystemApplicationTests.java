package org.cust.devicemanagesystem;

import org.cust.devicemanagesystem.mapper.UsersMapper;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.model.AuthorityCodeEnum;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceManageSystemApplication.class)
public class DeviceManageSystemApplicationTests {


    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private UsersMapper mapper;

    @Autowired
    private IAuthoritiesService authoritiesService;

    @Test
    public void contextLoads() {
        mapper.insert(new Users()
                .setInstitute("计算机科学技术学院")
                .setPassword(PasswordEncoder.encode("123123"))
                .setRealName("李四")
                .setTelPhone("13542500614")
                .setUsername("user")
                .setWorkId("1684316"));
    }

    @Test
    public void addAuthorities() {
        authoritiesService.saveBatch(Arrays.asList(new Authorities().setAuthority(AuthorityCodeEnum.SUPER_ADMIN.toString()).setUserId(1213451694000627713L),
                new Authorities().setAuthority(AuthorityCodeEnum.ADMIN.toString()).setUserId(1213451694000627713L),
                new Authorities().setAuthority(AuthorityCodeEnum.USER.toString()).setUserId(1213451694000627713L)));
    }
}
