package com.roncoo.eshop.cache.ha.controller;

import cn.hutool.http.HttpUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetCityNameCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfosCommand;
import com.roncoo.eshop.cache.ha.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

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


    /**
     * nginx开始，各级缓存都失效了，nginx发送很多的请求直接到缓存服务要求拉取最原始的数据
     *
     * @param productId
     * @return
     */
    @RequestMapping("/getProductInfo")
    public String getProductInfo(Long productId) {
        // 拿到一个商品ID
        // 调用商品服务的接口，获取商品ID对应商品的最新数据
        // 调用http接口

        HystrixCommand<ProductInfo> getProductInfoCommand = new GetProductInfoCommand(productId);
        ProductInfo productInfo = getProductInfoCommand.execute();

        Long cityId = productInfo.getCityId();
        String cityName = new GetCityNameCommand(cityId).execute();
        productInfo.setCityName(cityName);

        log.info(productInfo.toString());
        return "success";
    }

    /**
     * 一次性批量查询多条商品数据的请求
     */
    @RequestMapping("/getProductInfos")
    public String getProductInfos(String productIds) {
        HystrixObservableCommand<ProductInfo> getProductInfosCommand = new GetProductInfosCommand(productIds.split(","));
        Observable<ProductInfo> observable = getProductInfosCommand.observe();
        observable.subscribe(new Observer<ProductInfo>(){
            @Override
            public void onCompleted() {
                log.info("获取完了所有的商品数据");
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onNext(ProductInfo productInfo) {
                log.info(productInfo.toString());
            }
        });

        return "success";
    }


}
