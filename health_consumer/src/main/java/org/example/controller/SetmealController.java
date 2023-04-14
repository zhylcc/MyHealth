package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qiniu.common.QiniuException;
import org.example.constant.MessageConstant;
import org.example.constant.RedisConstant;
import org.example.entity.Result;
import org.example.pojo.Setmeal;
import org.example.service.SetmealService;
import org.example.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") - 1);
        String filename = UUID.randomUUID() + suffix;
        try {
            Map<String, Object> map = QiniuUtils.upload2Qiniu(imgFile.getBytes(), filename);
            map.put("uri", QiniuUtils.BUCKET_PREFIX + map.get("key"));
            // 将上传图片名称添加到“setmealPicResources”
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkGroupIds) {
        try {
            setmealService.add(setmeal, checkGroupIds);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findDetail")
    public Result findDetail(Integer id) {
        try {
            Setmeal setmeal = setmealService.findDetailById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
