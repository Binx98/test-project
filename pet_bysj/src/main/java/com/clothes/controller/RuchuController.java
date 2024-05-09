package com.clothes.controller;


import com.clothes.utils.R;
import com.clothes.utils.ResponseEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2024-05-09
 */
@RestController
@RequestMapping("/ruchu")
public class RuchuController {
    /**
     * 入库
     */
    @PostMapping("/ru")
    public R ru() {

        return R.out(ResponseEnum.SUCCESS);
    }

    /**
     * 出库
     */
    @PostMapping("/chu")
    public R chu() {
        return R.out(ResponseEnum.SUCCESS);
    }
}

