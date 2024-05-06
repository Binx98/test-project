package com.pet.controller;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.pet.pojo.Banner;
import com.pet.service.BannerService;
import com.pet.utils.R;
import com.pet.utils.ResponseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 公告 前端控制器
 * </p>
 *
 * @author Author
 * @since 2024-02-16
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 查询公告
     */
    @GetMapping("/list")
    public R list() {
        List<Banner> list = bannerService.list();
        return R.out(ResponseEnum.SUCCESS, list);
    }

    /**
     * 创建公告
     */
    @PostMapping("/save")
    public R save(String content, String url) {
        Banner banner = new Banner();
        banner.setId(IdWorker.getId());
        banner.setUrl(url);
        banner.setContent(content);
        banner.setCreateTime(LocalDateTime.now());
        bannerService.save(banner);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 修改公告
     */
    @PostMapping("/update")
    public R update(Long id, String content) {
        Banner banner = new Banner();
        banner.setContent(content);
        banner.setId(id);
        bannerService.updateById(banner);
        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 删除公告
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable Long id) {
        bannerService.removeById(id);
        return R.out(ResponseEnum.SUCCESS);
    }
}

