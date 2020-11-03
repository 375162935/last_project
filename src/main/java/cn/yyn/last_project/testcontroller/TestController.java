package cn.yyn.last_project.testcontroller;

import cn.yyn.last_project.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 杨以诺
 * by 2020-10-23 10:21
 */
@Controller
public class TestController {

    private static final String UPLOAD_PATH = "C:\\Users\\yyn\\Desktop\\毕业设计\\last_project\\src\\main\\resources\\static\\images\\commodity";

    @RequestMapping("/test")
    public String test01() {
        return "test";
    }

    //    @ResponseBody
    @RequestMapping("/testLoad")
    public String testLoad(@RequestParam(value = "img_file", required = false)
                                   MultipartFile img_file)  {
        try {
        System.out.println(img_file);
        System.out.println(img_file.getSize());
        }catch (MaxUploadSizeExceededException e){
            System.out.println(e.getMessage());
        }
//        if (img_file == null) {
//            System.out.println("请选择图片");
//        } else {
//            String upload = QiniuUtils.loadQiNiu(img_file);
//            System.out.println(upload);
//        }
        return "test";
    }

    @RequestMapping("/delete")
    public String test(String key){

        System.out.println(key);
        QiniuUtils.delImgQiNiu(key);
        return "test";
    }
}
