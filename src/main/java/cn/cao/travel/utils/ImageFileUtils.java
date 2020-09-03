package cn.cao.travel.utils;

import cn.cao.travel.entity.Place;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 通过路径获取文件
 * @author cao
 * @create 2020/9/2 - 15:35
 */
@Slf4j
public class ImageFileUtils {

    //通过路径获取图片，返回base64 编码的字符串
    public static String PathToBase64ImageFile(String path){
        File imgFile = new File(path);
        String base64 = "";
        try {
            byte[] bytes = FileUtils.readFileToByteArray(imgFile);
            base64 = Base64Utils.encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    //将list集合中的对象中某属性转换为 base64字符串
    public static List SetListPathToBases64(List<Place> lists){

        lists.forEach(place ->
                place.setPicPath(PathToBase64ImageFile(place.getPicPath()))
        );
        return lists;
    }

    //根据 绝对路径删除文件
    public static void deleteFiles(List<String> filesPath){
        //不能这么删除，万一是一个目录，不就全部删除了
        //filesPath.forEach(filePath -> FileUtils.deleteQuietly(new File(filePath)) );
        filesPath.forEach(filePath -> deleteFile(filePath));
        //filesPath.forEach(ImageFileUtils::deleteFile);

    }

    //删除一个文件
    public static void deleteFile(String filePath){

        File file = new File(filePath);
        if (!file.isDirectory()) {
            //删除成功返回 true
            boolean exists = file.exists();
            if(!file.delete()){
                //删除失败判断
                if(!exists){
                    log.info(file.getAbsolutePath() + " :文件不存在！");
                }else {
                    //删除失败且文件存在
                    log.info(file.getName() + " :删除失败,文件可能被锁定！");
                }
            }
        }else {
            log.info(file.getAbsolutePath() + " :是一个目录");
        }
    }

}
