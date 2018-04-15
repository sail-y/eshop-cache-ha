package com.roncoo.eshop.cache.ha.hystrix.command;

import cn.hutool.http.HttpUtil;
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
        return null;
    }


    @Override
    protected HystrixCommand<List<ProductInfo>> createCommand(Collection<CollapsedRequest<ProductInfo, Long>> collapsedRequests) {
        return null;
    }


    @Override
    protected void mapResponseToRequests(List<ProductInfo> batchResponse, Collection<CollapsedRequest<ProductInfo, Long>> collapsedRequests) {

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


            String params = requests.stream().map(CollapsedRequest::getArgument).map(Object::toString).collect(Collectors.joining(","));

            String url = "http://localhost:8082/getProductInfos?productIds=" + params;

            String response = HttpUtil.get(url);

            return JSONArray.parseArray(response, ProductInfo.class);
        }
    }

}
