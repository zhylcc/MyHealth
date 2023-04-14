package org.example.jobs;

import com.qiniu.common.QiniuException;
import org.example.constant.RedisConstant;
import org.example.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Jedis jedis = jedisPool.getResource();
        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (set != null) {
            for (String filename: set) {
                try {
                    QiniuUtils.deleteFromQiniu(filename);
                    jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
                } catch (QiniuException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
