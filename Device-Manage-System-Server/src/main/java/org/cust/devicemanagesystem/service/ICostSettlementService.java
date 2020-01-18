package org.cust.devicemanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cust.devicemanagesystem.model.CostSettlement;
import org.cust.devicemanagesystem.vo.CostSettlementVo;

/**
 * <p>
 * 费用结算 服务类
 * </p>
 *
 * @author Long
 * @since 2020-01-16
 */
public interface ICostSettlementService extends IService<CostSettlement> {

    IPage<CostSettlementVo> queryPage(IPage<CostSettlementVo> page, String userId);
}
