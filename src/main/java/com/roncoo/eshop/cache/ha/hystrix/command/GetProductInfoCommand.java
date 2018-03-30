package com.roncoo.eshop.cache.ha.hystrix.command;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

/**
 * 获取商品信息
 * <p>
 * 线程池隔离技术
 *
 * @author yangfan
 * @date 2018/03/18
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

    public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");

    private Long productId;

    public GetProductInfoCommand(Long productId) {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                .andCommandKey(KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(15)
                        .withQueueSizeRejectionThreshold(10))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 多少个请求以上才会判断断路器是否需要开启。
                        .withCircuitBreakerRequestVolumeThreshold(30)
                        // 错误的请求打到40%的时候就开始断路。
                        .withCircuitBreakerErrorThresholdPercentage(40)
                        // 3秒以后尝试恢复
                        .withCircuitBreakerSleepWindowInMilliseconds(4000))
        );

        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {

        System.out.println("调用接口查询商品数据，productId=" + productId);

        if (productId == -1) {
            throw new Exception();
        }

        String url = "http://localhost:8082/getProductInfo?productId=" + productId;
        String response = HttpUtil.get(url);
        System.out.println(response);
        return JSON.parseObject(response, ProductInfo.class);
    }

    @Override
    protected String getCacheKey() {
        return "product_info_" + productId;
    }

    @Override
    protected ProductInfo getFallback() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("降级商品");
        return productInfo;
    }

    public static void flushCache(Long productId) {
        HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(productId));
    }
}
