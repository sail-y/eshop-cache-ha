package com.roncoo.eshop.cache.ha.hystrix.command;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.roncoo.eshop.cache.ha.model.ProductInfo;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 批量查询多个商品数据的command
 *
 * @author yangfan
 * @date 2018/03/18
 */

public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {
    private String[] productIds;

    public GetProductInfosCommand(String[] productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoCommandGroup"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {

        return Observable.<ProductInfo>create(observer -> {
            try {
                for (String productId : productIds) {
                    String url = "http://localhost:8082/getProductInfo?productId=" + productId;
                    String response = HttpUtil.get(url);
                    ProductInfo productInfo = JSON.parseObject(response, ProductInfo.class);
                    observer.onNext(productInfo);

                }
                observer.onCompleted();
            } catch (Exception e) {
                observer.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }
}
