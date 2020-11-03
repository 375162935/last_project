package cn.yyn.last_project.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import com.google.gson.Gson;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 杨以诺
 * by 2020-10-22 19:47
 */
@Component
public class QiniuUtils {
    //当前文件的地址
    private static final String UPLOAD_PATH = "C:\\Users\\yyn\\Desktop\\毕业设计\\last_project\\src\\main\\resources\\static\\images\\commodity";
//    private static final String UPLOAD_PATH = "D:\\";

    public static String getUploadPath() {
        return UPLOAD_PATH;
    }

    // 密匙
    private static String accessKey = "18oFNtGFNjmHdgBinnprbFto-ay4qe7lCUgh6-gv";
    private static String secretKey = "KK0x9-IJH__FKNVOHZLDaoZu5fMHtFo7ajH7Gwy0";
    //工作空间
    private static String bucket = "yyn-last-project";
    //域名
    private static String prefix = "http://qilpqdaxg.hd-bkt.clouddn.com/";

    //上传图片获取地址
    public static File getNewFile(MultipartFile img_file)
            throws IOException {
        //1、文件在服务器上存储
        //先生成新的文件名
        UUID rid = UUID.randomUUID();
//        System.out.println(rid);
        //新建文件前缀名
        long uid = rid.getLeastSignificantBits();
//        System.out.println(uid);
        //取出文件名，进行字符串的拼接
        String filename = img_file.getOriginalFilename();
//        System.out.println(filename);
        //文件后缀名
        String suffix = filename.substring(filename.lastIndexOf('.'));
//        System.out.println(suffix);
        //新建文件名称
        File file = new File(UPLOAD_PATH + "/" + uid + suffix);
//        System.out.println(file.getName());
        //创建文件
        img_file.transferTo(file);
//        System.out.println(file);
        return file;
    }

    //上传图片
    public static String loadQiNiu(MultipartFile img_file) throws IOException {
        // 指定工作空间
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);
        // 指定大区
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        //图片上传
        File file = getNewFile(img_file);
        String upload = file.getName();//文件名称
        uploadManager.put(file, upload, token);//上传图片
        file.delete();//删除本地文件
        return upload;
    }

    //删除图片
    public static Response delImgQiNiu(String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return null;
    }
}
