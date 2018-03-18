package com.roncoo.eshop.cache.ha.hystrix.command;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.roncoo.eshop.cache.ha.cache.local.LocationCache;
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

    private Long productId;

    public GetProductInfoCommand(Long productId) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoCommandGroup"));
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() {

        String url = "http://localhost:8082/getProductInfo?productId=" + productId;
        String response = HttpUtil.get(url);
        System.out.println(response);

        return JSON.parseObject(response, ProductInfo.class);
    }
}
