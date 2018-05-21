package cn.mrpei.common.utils;

import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author peizhouyu (大数据部-大数据研发部-平台产品研发部)
 * @version V1.0.0
 * @description
 * @date 2018/5/21
 * @last-modified ：
 * @class cn.mrpei.common.utils
 * @copyright Copyright © 2004-2018 京东JD.com ALL Right Reserved
 * @see
 */
public class FileUtil {
    public static String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
//        log.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新的文件名:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功

            //将targetFile 上传到FTP 服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完之后，删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {

            return null;
        }
        return targetFile.getName();
    }
}
