package com.roncoo.eshop.cache.ha.controller;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangfan
 * @date 2018/03/11
 */
@RestController
@Slf4j
public class CacheController {


    @RequestMapping("/change/product")
    public String changeProduct(Long productId) {
        // 拿到一个商品ID
        // 调用商品服务的接口，获取商品ID对应商品的最新数据
        // 调用http接口

        String url = "http://localhost:8082/getProductInfo?prodcutId=" + productId;
        String response = HttpUtil.get(url);

        log.info(response);

        return "success";
    }
}
