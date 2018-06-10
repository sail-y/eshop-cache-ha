package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoCommand;
import com.roncoo.eshop.cache.ha.hystrix.command.GetProductInfoFacadeCommand;
import org.junit.Test;

/**
 * @author yangfan
 * @date 2018/04/15
 */
public class MultiLevelFallbackTest {

    @Test
    public void test() {
        GetProductInfoFacadeCommand getProductInfoCommand1 = new GetProductInfoFacadeCommand(-1L);
        System.out.println(getProductInfoCommand1.execute());
        GetProductInfoFacadeCommand getProductInfoCommand2 = new GetProductInfoFacadeCommand(-2L);
        System.out.println(getProductInfoCommand2.execute());
    }
}
