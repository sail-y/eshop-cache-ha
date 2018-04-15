package com.roncoo.eshop.cache.ha.hystrix.command;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.roncoo.eshop.cache.ha.model.ProductInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangfan
 * @date 2018/04/15
 */
public class GetProductInfosCollapser extends HystrixCollapser<List<ProductInfo>, ProductInfo, Long> {

    private Long productId;


    public GetProductInfosCollapser(Long productId) {
        this.productId = productId;
    }

    @Override
    public Long getRequestArgument() {
        return productId;
    }


    @Override
    protected HystrixCommand<List<ProductInfo>> createCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
        String params = requests.stream().map(CollapsedRequest::getArgument).map(Object::toString).collect(Collectors.joining(","));
        System.out.println("createCommand方法执行，params=" + params);
        return new BatchCommand(requests);
    }


    @Override
    protected void mapResponseToRequests(List<ProductInfo> batchResponse, Collection<CollapsedRequest<ProductInfo, Long>> requests) {
        int count = 0;
        for (CollapsedRequest<ProductInfo, Long> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<ProductInfo>> {

        public final Collection<CollapsedRequest<ProductInfo, Long>> requests;

        public BatchCommand(Collection<CollapsedRequest<ProductInfo, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfosCollapserBatchCommand")));
            this.requests = requests;
        }

        @Override
        protected List<ProductInfo> run() throws Exception {

            // 将一个批次内的商品id给拼接到了一起
            String params = requests.stream().map(CollapsedRequest::getArgument).map(Object::toString).collect(Collectors.joining(","));

            // 将多个商品id合并到一个batch内，直接发送一次网络请求，获取所有的商品
            String url = "http://localhost:8082/getProductInfos?productIds=" + params;

            String response = HttpUtil.get(url);

            List<ProductInfo> productInfos = JSONArray.parseArray(response, ProductInfo.class);
            for (ProductInfo productInfo : productInfos) {
                System.out.println("BatchCommand内部， productInfo=" + JSON.toJSONString(productInfo));
            }
            return productInfos;
        }
    }

}
