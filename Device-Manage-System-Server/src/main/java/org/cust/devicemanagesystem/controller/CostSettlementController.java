package org.cust.devicemanagesystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.cust.devicemanagesystem.model.CostSettlement;
import org.cust.devicemanagesystem.service.ICostSettlementService;
import org.cust.devicemanagesystem.vo.CostSettlementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 费用结算 前端控制器
 * </p>
 *
 * @author Long
 * @since 2020-01-16
 */
@Slf4j
@RestController
@RequestMapping("/costSettlement")
public class CostSettlementController {


    private final ICostSettlementService costSettlementService;

    /**
     * 新增
     */
    @PostMapping("/save")
    public boolean save(@RequestBody @Validated CostSettlement costSettlement) {
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
    @PutMapping("/update")
    public boolean updateById(@RequestBody @Validated CostSettlement costSettlement) {
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
    @PostMapping("/page/{userId}")
    public IPage<CostSettlementVo> page(@RequestBody Page<CostSettlementVo> page, @PathVariable String userId) {
        if (Objects.equals(userId, "all")) {
            //管理员查询所有结算记录
            return costSettlementService.queryPage(page, null);
        }
        return costSettlementService.queryPage(page, userId);
    }


    @Autowired
    public CostSettlementController(ICostSettlementService costSettlementService) {
        this.costSettlementService = costSettlementService;
    }
}

