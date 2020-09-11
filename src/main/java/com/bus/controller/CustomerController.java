package com.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bus.domain.Customer;
import com.bus.service.CustomerService;
import com.bus.vo.CustomerVo;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.controller
 * @ClassName: CustomerController
 * @Author: 游佳琪
 * @Description: Customer 前端控制层
 * @Date: 2020-9-3 16:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    /**
     * 功能描述: <br>
     * 〈〉           全查询
     *
     * @Param:
     * @Return:
     * @Author: YJQ
     * @Date: 2020-8-17 09:11
     */
    @RequestMapping("/loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo) {
//        创建选择器
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
//        创建page,传入当前页和限制页数据
        IPage<Customer> page = new Page<>(customerVo.getPage(), customerVo.getLimit());
//        设置查询条件
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getCustomername()), "customername", customerVo.getCustomername());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getConnectionperson()), "connectionperson", customerVo.getConnectionperson());
        queryWrapper.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone());
        queryWrapper.orderByAsc("id");
        this.customerService.page(page, queryWrapper);
//        以分页的形式返回查询结果数据
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加客户
     *
     * @Param: [customerVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("/addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo) {
        try {
            this.customerService.save(customerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉           更新客户信息
     *
     * @Param: [customerVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-9-4 21:10
     */

    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo) {
        try {
            this.customerService.updateById(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 功能描述: <br>
     * 〈〉       删除操作
     *
     * @Param: [customerVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:28
     */
    @RequestMapping("/deleteCustomer")
    public ResultObj deleteCustomer(CustomerVo customerVo) {
        try {
            this.customerService.removeById(customerVo.getId());
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉           批量删除
     *
     * @Param: [customerVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-18 15:36
     */

    @RequestMapping("/batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(CustomerVo customerVo) {
        try {
            Collection<Serializable> idlist = new ArrayList<>();
            for (Integer id : customerVo.getIds()) {
                idlist.add(id);
            }
            this.customerService.removeByIds(idlist);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
}
