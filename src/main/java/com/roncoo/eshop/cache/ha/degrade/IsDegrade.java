package com.roncoo.eshop.cache.ha.degrade;

/**
 * @author yangfan
 * @date 2018/04/15
 */
public class IsDegrade {

    private static boolean degrade = false;

    public static boolean isDegrade() {
        return degrade;
    }

    public static void setDegrade(boolean degrade) {
        IsDegrade.degrade = degrade;
    }

}

