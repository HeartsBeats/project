package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.DataGridView;
import com.example.common.ResultObj;
import com.example.common.WebUtils;
import com.example.entity.Notice;
import com.example.entity.User;
import com.example.service.NoticeService;
import com.example.vo.LoginfoVo;
import com.example.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @ProjectName: project-demo
 * @Package: com.example.controller
 * @ClassName: NoticeControoler
 * @Author: 游佳琪
 * @Description: 公告控制器
 * @Date: 2020-8-18 20:41
 * @Version: 1.0
 */
@RestController
@RequestMapping("/notice")
public class NoticeControoler {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo) {
//        分页查询
        IPage<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
//        模糊查询
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title", noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()), "opername", noticeVo.getOpername());
        queryWrapper.ge(noticeVo.getStartTime() != null, "createtime", noticeVo.getStartTime());
        queryWrapper.le(noticeVo.getEndTime() != null, "createtime", noticeVo.getEndTime());
//        对查询结果进行排序
        queryWrapper.orderByDesc("createtime");
//        执行分页查询
        this.noticeService.page(page, queryWrapper);
//        包装结果集
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 功能描述: <br>
     * 〈〉           添加公告
     *
     * @Param: [noticeVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-20 08:24
     */

    @RequestMapping("addNotice")
    public ResultObj addNotice(NoticeVo noticeVo) {
        try {
            noticeVo.setCreatetime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            noticeVo.setOpername(user.getName());
            this.noticeService.save(noticeVo);
            return ResultObj.ADD_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉               修改公告
     *
     * @Param: [noticeVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-20 08:46
     */

    @RequestMapping("updateNotice")
    public ResultObj updateNotice(NoticeVo noticeVo) {
        try {
            noticeVo.setCreatetime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            noticeVo.setOpername(user.getName());
            this.noticeService.updateById(noticeVo);
            return ResultObj.ADD_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉           删除公告操作
     *
     * @Param: [noticeVoo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-20 08:47
     */

    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(NoticeVo noticeVoo) {
        try {
            this.noticeService.removeById(noticeVoo.getId());
            return ResultObj.DELETE_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 功能描述: <br>
     * 〈〉           批量删除操作
     *
     * @Param: [noticeVo]
     * @Return: com.example.common.ResultObj
     * @Author: YJQ
     * @Date: 2020-8-20 08:49
     */

    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(NoticeVo noticeVo) {
        try {
            Collection<Serializable> idlist = new ArrayList<>();
            for (Integer id : noticeVo.getIds()) {
                idlist.add(id);
            }
            this.noticeService.removeByIds(idlist);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }

    }
    /**
     * 根据公告ID查询一条公告
     * @param id    公告ID
     * @return
     */
    @RequestMapping("loadNoticeById")
    public DataGridView loadNoticeById(Integer id){
        Notice notice = noticeService.getById(id);
        return new DataGridView(notice);
    }
}
