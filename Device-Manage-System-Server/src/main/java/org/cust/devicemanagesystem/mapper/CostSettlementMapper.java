package org.cust.devicemanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.*;
import org.cust.devicemanagesystem.config.handler.StringIdHandler;
import org.cust.devicemanagesystem.model.CostSettlement;
import org.cust.devicemanagesystem.vo.CostSettlementVo;

/**
 * 费用结算 Mapper 接口
 *
 * @author Long
 * @since 2020-01-16
 */
@Mapper
public interface CostSettlementMapper extends BaseMapper<CostSettlement> {

  @Select(
      "<script>"
          + "SELECT id,"
          + "user_id,"
          + "device_name,"
          + "device_serial_number,"
          + "device_type,"
          + "reservation_start_time,"
          + "reservation_stop_time,"
          + "state,"
          + "cost_sum,"
          + "device_manager_name,"
          + "device_manager_contact "
          + "FROM cost_settlement"
          + "<if test='userId != null'>"
          + "where user_id = #{userId}"
          + "</if>"
          + "</script>")
  @Results({
    @Result(property = "id", column = "id", typeHandler = StringIdHandler.class),
    @Result(property = "userId", column = "user_id", typeHandler = StringIdHandler.class),
    @Result(property = "costSum", column = "cost_sum", typeHandler = StringIdHandler.class)
  })
  IPage<CostSettlementVo> queryPage(IPage<CostSettlementVo> page, @Param("userId") String userId);
}
