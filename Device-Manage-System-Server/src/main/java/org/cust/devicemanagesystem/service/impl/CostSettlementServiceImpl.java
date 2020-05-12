package org.cust.devicemanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cust.devicemanagesystem.mapper.CostSettlementMapper;
import org.cust.devicemanagesystem.model.CostSettlement;
import org.cust.devicemanagesystem.service.ICostSettlementService;
import org.cust.devicemanagesystem.vo.CostSettlementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 费用结算 服务实现类
 *
 * @author Long
 * @since 2020-01-16
 */
@Service
public class CostSettlementServiceImpl extends ServiceImpl<CostSettlementMapper, CostSettlement>
    implements ICostSettlementService {

  private final CostSettlementMapper CostSettlementMapper;

  @Override
  public IPage<CostSettlementVo> queryPage(IPage<CostSettlementVo> page, String userId) {
    return CostSettlementMapper.queryPage(page, userId);
  }

  @Autowired
  public CostSettlementServiceImpl(
      org.cust.devicemanagesystem.mapper.CostSettlementMapper costSettlementMapper) {
    CostSettlementMapper = costSettlementMapper;
  }
}
