package org.example.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.util.HashMap;
import java.util.Map;

public class QiniuUtils {
//    七牛云鉴权密钥
    public static final String ACCESS_KEY = "w1uO7Ey6nBEwmoNM3DkEaSdcSrxQ1SMMLEw-vFi9";
    public static final String SECRET_KEY = "t74taFmOs4nYZpokozKjzZpXGK3jLmRs1VwLvuEv";
//    对象桶
    public static final String BUCKET_NAME = "oss-myhealth";
    public static final String BUCKET_PREFIX = "http://rflyq3wmr.hb-bkt.clouddn.com/";

//    上传文件
    public static Map<String, Object> upload2Qiniu(byte[] bytes, String filename) throws QiniuException {
        UploadManager uploadManager = new UploadManager(new Configuration(Region.huabei()));
        String upToken = Auth.create(ACCESS_KEY, SECRET_KEY).uploadToken(BUCKET_NAME);
        HashMap<String, Object> map = new HashMap<>();
        Response response = uploadManager.put(bytes, filename, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        map.put("key", putRet.key);
        map.put("hash", putRet.hash);
        return map;
    }

//    删除文件
    public static void deleteFromQiniu(String filename) throws QiniuException {
        BucketManager bucketManager = new BucketManager(
                Auth.create(ACCESS_KEY, SECRET_KEY),
                new Configuration(Region.huabei()));
        HashMap<String, Object> map = new HashMap<>();
        bucketManager.delete(BUCKET_NAME, filename);
    }
}