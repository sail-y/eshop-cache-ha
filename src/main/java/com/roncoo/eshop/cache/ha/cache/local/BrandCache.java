package com.roncoo.eshop.cache.ha.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfan
 * @date 2018/03/18
 */
public class BrandCache {
    private static Map<Long, String> brandMap = new HashMap<Long, String>(){
        {
            put(1L, "iPhone");
        }
    };


    public static String getBrandName(Long brancId) {
        return brandMap.get(brancId);
    }
}
