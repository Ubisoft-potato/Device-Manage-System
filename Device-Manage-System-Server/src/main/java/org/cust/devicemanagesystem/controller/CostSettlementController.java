package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.cust.devicemanagesystem.model.CostSettlement;
import org.cust.devicemanagesystem.service.ICostSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 费用结算 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-16
 */
@RestController
@RequestMapping("/costSettlement")
public class CostSettlementController {


    private final ICostSettlementService costSettlementService;


    /**
     * 新增
     */
    @PostMapping
    public boolean save(CostSettlement costSettlement) {
        return costSettlementService.save(costSettlement);
    }

    /**
     * 通过id删除
     */
    @DeleteMapping
    public boolean delete(CostSettlement costSettlement) {
        return costSettlementService.removeById(costSettlement.getId());
    }

    /**
     * 修改
     */
    @PutMapping
    public boolean updateById(CostSettlement costSettlement) {
        return costSettlementService.updateById(costSettlement);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<CostSettlement> list(CostSettlement costSettlement) {
        QueryWrapper<CostSettlement> wp = new QueryWrapper<>();
        //todo init wp
        return costSettlementService.list(wp);
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public IPage<CostSettlement> page(Page<CostSettlement> page) {
        return costSettlementService.page(page);
    }


    @Autowired
    public CostSettlementController(ICostSettlementService costSettlementService) {
        this.costSettlementService = costSettlementService;
    }
}

