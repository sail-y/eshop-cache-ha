package com.roncoo.eshop.cache.ha;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

/**
 * @author yangfan
 * @date 2018/03/29
 */
public class CircuitBreakerTest {

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            String response = HttpUtil.get("http://localhost:8081/getProductInfo?productId=1");
            System.out.println("第" + (i + 1) + "次请求，结果为：" + response);
        }

        for (int i = 0; i < 25; i++) {
            String response = HttpUtil.get("http://localhost:8081/getProductInfo?productId=-1");
            System.out.println("第" + (i + 1) + "次请求，结果为：" + response);
        }
        System.out.println("等待几秒钟，统计到最近30次请求超过40%次，开启断路");
        Thread.sleep(4000);

        // 等待五秒后，时间窗口统计了（withCircuitBreakerSleepWindowInMilliseconds），发现异常比例太多，这个时候才会去开启断路器。直接走断路器
        for (int i = 0; i < 10; i++) {
            String response = HttpUtil.get("http://localhost:8081/getProductInfo?productId=1");
            System.out.println("第" + (i + 1) + "次请求，结果为：" + response);
        }

        // 断路器有一个时间窗口，我们必须要等到那个个时间窗口过了以后，hystrix才会看一下最近的时间窗口
        // 比如说最近的10秒内有多少条数据其中一场的数据有没有到一定的比例，如果到了一定的比例，才会去断路

        System.out.println("尝试等待5秒钟，等待恢复");

        Thread.sleep(5000);

        for (int i = 0; i < 10; i++) {
            String response = HttpUtil.get("http://localhost:8081/getProductInfo?productId=1");
            System.out.println("第" + (i + 1) + "次请求，结果为：" + response);
        }
    }
}
