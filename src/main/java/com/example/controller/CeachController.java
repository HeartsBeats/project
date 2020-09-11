package com.example.controller;

import com.example.cache.CachePool;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.example.entity.CacheBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: CeachController
 * @Author: 游佳琪
 * @Description: 缓存处理控制器
 * @Date: 2020-9-11 07:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("/cache")
public class CeachController {
    //    获取缓存数据
    private volatile static Map<String, Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;

    @RequestMapping("/loadAllCache")
    public DataGridView loadAllCeach() {
        Set<Map.Entry<String, Object>> entries = CACHE_CONTAINER.entrySet();
        List<CacheBean> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : entries) {
            list.add(new CacheBean(entry.getKey(), entry.getValue()));
        }
        return new DataGridView(list);
    }
    /**
     * 删除缓存
     */
    @RequestMapping("deleteCache")
    public ResultObj deleteCache(String key) {
        CachePool.removeCeachByKey(key);
        return ResultObj.DELETE_SUCCESS;
    }

    /**
     * 清空缓存
     */
    @RequestMapping("removeAllCache")
    public ResultObj removeAllCache() {
        CachePool.removeAllCeach();
        return ResultObj.DELETE_SUCCESS;
    }
    /**
     * 同步缓存
     */
    @RequestMapping("syncCache")
    public ResultObj syncCache() {
        CachePool.syncData();
        return ResultObj.OPERATE_SUCCESS;
    }

}
