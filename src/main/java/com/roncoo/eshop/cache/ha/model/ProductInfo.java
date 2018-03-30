package com.roncoo.eshop.cache.ha.model;

import lombok.Data;

/**
 * 商品信息
 *
 * @author Administrator
 */
@Data
public class ProductInfo {

    private Long id;
    private String name;
    private Double price;
    private String pictureList;
    private String specification;
    private String service;
    private String color;
    private String size;
    private Long shopId;
    private String modifiedTime;
    private Long cityId;
    private String cityName;
    private Long brandId;
    private String brandName;


}
