package com.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bus.domain.Outport;

/**
 * @ProjectName: project-demo
 * @Package: com.bus.service
 * @ClassName: InportService
 * @Author: 游佳琪
 * @Description: Inport 服务层
 * @Date: 2020-9-3 16:06
 * @Version: 1.0
 */
public interface OutportService extends IService<Outport> {
    void addOutPort(Integer id, Integer currentnumber, String remark);
}
