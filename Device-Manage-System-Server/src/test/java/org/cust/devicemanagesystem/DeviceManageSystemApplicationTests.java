package org.cust.devicemanagesystem;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.cust.devicemanagesystem.mapper.DeviceMapper;
import org.cust.devicemanagesystem.mapper.ReservationDeviceMapper;
import org.cust.devicemanagesystem.mapper.UsersMapper;
import org.cust.devicemanagesystem.model.Authorities;
import org.cust.devicemanagesystem.model.AuthorityCodeEnum;
import org.cust.devicemanagesystem.model.Users;
import org.cust.devicemanagesystem.service.IAuthoritiesService;
import org.cust.devicemanagesystem.vo.ReservationDeviceVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceManageSystemApplication.class)
public class DeviceManageSystemApplicationTests {

  @Autowired private PasswordEncoder PasswordEncoder;

  @Autowired private UsersMapper mapper;

  @Autowired private DeviceMapper deviceMapper;

  @Autowired private ReservationDeviceMapper reservationDeviceMapper;

  @Autowired private IAuthoritiesService authoritiesService;

  @Test
  public void contextLoads() {
    mapper.insert(
        new Users()
            .setInstitute("计算机科学技术学院")
            .setPassword(PasswordEncoder.encode("123123"))
            .setRealName("张三")
            .setTelPhone("13542500614")
            .setUsername("admin")
            .setWorkId("1684316"));
  }

  @Test
  public void addAuthorities() {
    authoritiesService.saveBatch(
        Arrays.asList(
            new Authorities()
                .setAuthority(AuthorityCodeEnum.SUPER_ADMIN.toString())
                .setUserId(1213699955961700354L),
            new Authorities()
                .setAuthority(AuthorityCodeEnum.ADMIN.toString())
                .setUserId(1213699955961700354L),
            new Authorities()
                .setAuthority(AuthorityCodeEnum.USER.toString())
                .setUserId(1213699955961700354L)));
  }

  @Test
  public void joinTest() {
    System.out.println(
        reservationDeviceMapper
            .queryReservationPage(
                new Page<ReservationDeviceVo>().setCurrent(1).setSize(5),
                "CHECKING",
                "1215085298749313026")
            .getRecords());
  }
}
