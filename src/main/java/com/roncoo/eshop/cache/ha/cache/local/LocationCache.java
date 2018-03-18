package com.roncoo.eshop.cache.ha.cache.local;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangfan
 * @date 2018/03/18
 */
public class LocationCache {
    private static Map<Long, String> cityMap = new HashMap<Long, String>(){
        {
            put(1L, "北京");
        }
    };


    public static String getCityName(Long cityId) {
        return cityMap.get(cityId);
    }
}
