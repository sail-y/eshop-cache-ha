package com.roncoo.eshop.cache.ha;

import cn.hutool.http.HttpUtil;

/**
 *
 * 请求合并测试
 *
 */
public class RequestCollapserTest {

    public static void main(String[] args) {
        HttpUtil.get("http://localhost:8081/getProductInfos?productIds=1,2,3,4,5,6,7");
    }
}
