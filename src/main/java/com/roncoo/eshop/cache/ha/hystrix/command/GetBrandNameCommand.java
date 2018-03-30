package com.roncoo.eshop.cache.ha.hystrix.command;

import com.netflix.hystrix.*;
import com.roncoo.eshop.cache.ha.cache.local.BrandCache;

/**
 * @author yangfan
 * @date 2018/03/29
 */
public class GetBrandNameCommand extends HystrixCommand<String> {

    private Long brandId;

    public GetBrandNameCommand(Long brandId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandInfoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetBrandNameCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetBrandInfoPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(15)
                        .withQueueSizeRejectionThreshold(10))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withFallbackIsolationSemaphoreMaxConcurrentRequests(15))
        );
        this.brandId = brandId;
    }

    @Override
    protected String run() throws Exception {
        // 调用一个品牌服务的接口
        // 如果调用失败了，报错了，那么就会去调用fallback降级机制
        throw new Exception();
    }

    @Override
    protected String getFallback() {
        System.out.println("从本地缓存获取过期的品牌数据，brandId=" + brandId);
        return BrandCache.getBrandName(brandId);
    }

}
