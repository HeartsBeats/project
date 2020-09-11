package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.example.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service.imp
 * @ClassName: NoticeServiceImpl
 * @Author: 游佳琪
 * @Description: Noctice类服务层接口实现类
 * @Date: 2020-8-18 20:39
 * @Version: 1.0
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
