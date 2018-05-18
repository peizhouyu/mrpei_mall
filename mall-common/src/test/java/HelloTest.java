import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/17
 * @last-modified ：
 * @class PACKAGE_NAME
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class HelloTest {
    @Test
    public void testJedisSingle() {
        Jedis jedis = new Jedis("1127.0.0.1", 6379);
        jedis.set("name", "mrpei");
        String name = jedis.get("name");
        System.out.println(name);
        jedis.close();
    }

}
