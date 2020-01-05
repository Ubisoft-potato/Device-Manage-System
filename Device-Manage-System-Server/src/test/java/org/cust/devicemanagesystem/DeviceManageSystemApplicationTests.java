package org.cust.devicemanagesystem;

import org.cust.devicemanagesystem.mapper.UsersMapper;
import org.cust.devicemanagesystem.model.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceManageSystemApplication.class)
public class DeviceManageSystemApplicationTests {


    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private UsersMapper mapper;

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
}
