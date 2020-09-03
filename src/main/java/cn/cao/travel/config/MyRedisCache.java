package cn.cao.travel.config;

import ch.qos.logback.core.util.TimeUtil;
import cn.cao.travel.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

/**
 * redis缓存
 * @author cao
 * @create 2020/9/2 - 21:44
 */
public class MyRedisCache implements Cache {

    ////当前放入缓存的mapper的namespace
    private String id;

    public MyRedisCache(String id) {
        this.id = id;
    }
    // 返回cache唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    ////存入缓存  redis RedisTemplate StringRedisTemplate
    @Override
    public void putObject(Object key, Object value) {

        getRedisTemplate().opsForHash().put(id, getKeyToMD5(key.toString()),value);
        getRedisTemplate().expire(id,30, TimeUnit.MINUTES);//30分钟的超时时间
    }

    //获取中获取数据
    @Override
    public Object getObject(Object key) {

        return getRedisTemplate().opsForHash().get(id, getKeyToMD5(key.toString()) );

    }






    //该方法为mybatis保留方法 默认没有实现 后续版本可能会实现
    @Override
    public Object removeObject(Object key) {
        return null;
    }




    @Override
    public void clear() {
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        return getRedisTemplate().opsForHash().size(id).intValue();
    }



    //封装redisTemplate
    private RedisTemplate getRedisTemplate(){
        //通过application工具类获取redisTemplate
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        //assert (redisTemplate == null);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }


    //优化策略，用 MD5 加密mybatis生成的 key
    private String getKeyToMD5(String key){
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
