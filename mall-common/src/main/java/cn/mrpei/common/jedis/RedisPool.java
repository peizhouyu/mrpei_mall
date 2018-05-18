package cn.mrpei.common.jedis;


import cn.mrpei.common.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/18
 * @last-modified ：
 * @class cn.mrpei.common.exception
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class RedisPool {
    //jedis 连接池
    private static JedisPool pool;

    //IP
    private static String redisIp = PropertiesUtil.getProperty("redis1.ip");
    //port
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    //password
    private static String redisPassword = PropertiesUtil.getProperty("redis1.password");
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    //最大空闲连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));
    //最小空闲连接数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));
    //获取连接实例前是否 验证连接实例可用性 true为可用
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
    //归还连接实例前是否 验证连接实例可用性 true为可用
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        //连接耗尽时是否阻塞， false 会抛出异常 ，默认为 true 阻塞直到超时 超时异常
        config.setBlockWhenExhausted(true);

        pool = new JedisPool(config,redisIp,redisPort,1000*2,redisPassword);

    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
        //if (jedis != null) 源码已做判断 下同
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

//    public static void main(String[] args) {
//        System.out.println(redisIp);
//        Jedis jedis = pool.getResource();
//        jedis.set("ee","123");
//        returnResource(jedis);
//        pool.destroy();
//        System.out.println("end");
//    }


}
