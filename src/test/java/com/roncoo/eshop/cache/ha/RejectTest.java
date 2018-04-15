package com.roncoo.eshop.cache.ha;

import cn.hutool.http.HttpUtil;

/**
 *
 * 限流测试
 *
 * @author yangfan
 * @date 2018/03/30
 */
public class RejectTest {

    public static void main(String[] args) {
        // GetProductInfoCommand配置线程池大小10，队列长度为12，超过8以后会被拒。


        // 先进去线程池的是10个请求，然后有8个请求进入等待队列，线程池里有空闲，等待队列中的请求如果还没有timeout，那么就进去线程池去执行
        // withExecutionTimeoutInMilliseconds(20000)：timeout也设置大一些，否则如果请求放等待队列中时间太长了，直接就会timeout，等不到去线程池里执行了
        // withFallbackIsolationSemaphoreMaxConcurrentRequests(30)：fallback，sempahore限流，30个，避免太多的请求同时调用fallback被拒绝访问
        for (int i = 0; i < 25; i++) {
            int finalI = i;
            new Thread(() -> {
                String response = HttpUtil.get("http://localhost:8081/getProductInfo?productId=-2");
                System.out.println("第" + (finalI + 1) + "次请求，结果为：" + response);
            }).start();
        }
    }
}
