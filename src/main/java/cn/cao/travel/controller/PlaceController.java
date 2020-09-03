package cn.cao.travel.controller;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Place;
import cn.cao.travel.entity.Result;
import cn.cao.travel.service.PlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author cao
 * @create 2020/9/1 - 22:55
 */
@RestController
@RequestMapping("place")
@CrossOrigin
@Slf4j
public class PlaceController {

    @Resource
    private PlaceService placeServiceImpl;

    @Value("${upload.dir}")
    private String realPath;


    //修改
    @PostMapping("update")
    public Result update(@RequestParam(required = false) MultipartFile image,Place place){
        Result result = new Result();
        //如果更新了图片
        if(null != image){
            //文件上传
            String imageName = image.getOriginalFilename();//获取图片名称
            //String extension = FilenameUtils.getExtension(imageName);//拿到文件扩展名
            String newImageName = UUID.randomUUID().toString().replaceAll("-", "") +"-"+imageName;
            File fileStoragePath = new File(realPath,newImageName);//文件存储路径
            try {
                image.transferTo(fileStoragePath);
                place.setPicPath(newImageName);//设置存储文件夹路径
                result.setMessage("更新成功！");
            } catch (IOException e) {
                log.error(place+"==>"+e.getMessage());
                result.setState(false).setMessage("更新失败");
            }

        }

        try {
            placeServiceImpl.updatePlace(place);
            result.setMessage("更新成功！");
        } catch (Exception e) {
            result.setState(false).setMessage(e.getMessage());
        }

        log.info("本次上传图："+image);
        log.info("本次更新信息：\r\n"+place);

        return result;
    }


    //删除
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){

        Result result = new Result();

        try {
            placeServiceImpl.deleteByPlace(id);
            result.setMessage("删除成功！");
        } catch (Exception e) {
            log.error(id+":=>"+e.getMessage());
            result.setState(false).setMessage("删除失败");
        }

        return result;
    }


    //保存景点
    @PostMapping("save")
    public Result save(MultipartFile image, Place place)  {
        Result result = new Result();

        //文件上传
        String imageName = image.getOriginalFilename();//获取图片名称
        //String extension = FilenameUtils.getExtension(imageName);//拿到文件扩展名
        String newImageName = UUID.randomUUID().toString().replaceAll("-", "") +"-"+imageName;

        File fileStoragePath = new File(realPath,newImageName);//文件存储路径

        try {
            image.transferTo(fileStoragePath);
            //将文件的绝对路径存储到 place 对象中
            //place.setPicPath(fileStoragePath.getAbsolutePath());
            place.setPicPath(newImageName);//设置存储文件夹路径
            placeServiceImpl.save(place);
            result.setMessage("保存成功");
        } catch (Exception e) {
            result.setState(false).setMessage(e.getMessage());
        }

        return result;
    }


    //查询一个景点
    //根据 景点id查询景点
    @GetMapping("queryPlace/{id}")
    public Place queryPlace(@PathVariable(value = "id") Integer id){

        return placeServiceImpl.queryPlaceById(id);

    }


    //分页查询
    @GetMapping("queryByPage")
    public Pagination queryByPage(@RequestParam(value = "currentPage",defaultValue = "1") Integer currentPage,
                                  @RequestParam(value = "pageSize",defaultValue = "3") Integer pageSize,
                                  @RequestParam("provinceId")Integer provinceId){

        Pagination page = placeServiceImpl.queryByProvinceIdPage(currentPage, pageSize, provinceId);



        return page;
    }

}
