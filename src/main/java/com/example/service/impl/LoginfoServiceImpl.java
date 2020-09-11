package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Loginfo;
import com.example.mapper.LoginfoMapper;
import com.example.service.LoginfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: project-demo
 * @Package: com.example.service.imp
 * @ClassName: LoginfoServiceImpl
 * @Author: 游佳琪
 * @Description: loginfo服务器接口实现类
 * @Date: 2020-8-17 09:04
 * @Version: 1.0
 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {
}
