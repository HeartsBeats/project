package com.example.entity;

import com.alibaba.fastjson.JSON;

/**
 * @ProjectName: project-demo
 * @Package: com.example.entity
 * @ClassName: CacheBean
 * @Author: 游佳琪
 * @Description: 缓存类
 * @Date: 2020-9-11 08:05
 * @Version: 1.0
 */
public class CacheBean {
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public CacheBean() {
    }

    public CacheBean(String key, Object value) {
        super();
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        this.value = JSON.toJSON(value).toString();return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
