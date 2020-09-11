package com.bus.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: BusinessController
 * @Author: 游佳琪
 * @Description: Business 前端跳转控制器
 * @Date: 2020-9-3 16:16
 * @Version: 1.0
 */
@Controller
@RequestMapping("/bus")
@Slf4j
public class BusinessController {


    /**
     * 跳转到客户管理
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "business/customer/CustomerManager";
    }

    /**
     * 跳转到客户管理
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager() {
        return "business/provider/ProviderManager";
    }

    /**
     * 跳转到商品管理
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager() {
        return "business/goods/GoodsManager";
    }
    /**
     * 跳转到进货管理管理
     */
    @RequestMapping("toInportManager")
    public String toInportManager() {
        return "business/inport/InportManager";
    }
    /**
     * 跳转到退货查询页面
     */
    @RequestMapping("/toOutportManager")
    public String toOutportManager() {
        return "business/outport/OutportManager";
    }
}
