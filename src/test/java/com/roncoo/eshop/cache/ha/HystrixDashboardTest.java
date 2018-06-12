package com.roncoo.eshop.cache.ha;

import cn.hutool.http.HttpUtil;

/**
 * 监控请求测试
 *
 * @author yangfan
 * @date 2018/06/10
 */
public class HystrixDashboardTest {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            HttpUtil.get("http://localhost:8081/getProductInfo?productId=1");
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
