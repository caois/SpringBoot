package cn.cao.travel.controller;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Province;
import cn.cao.travel.entity.Result;
import cn.cao.travel.service.ProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cao
 * @create 2020/9/1 - 16:49
 */
@RestController
@RequestMapping("province")
@CrossOrigin
@Slf4j
public class ProvinceController {


    @Resource
    private ProvinceService provinceServiceImpl;


    //更新
    @PostMapping("update")
    public Result update(@RequestBody Province province){
        Result result = new Result();

        try {
            provinceServiceImpl.update(province);
            result.setState(true).setMessage("更新成功！！");
        } catch (Exception e) {
            log.info("province="+province);
            log.info("error==>"+e.getMessage());
            result.setState(false).setMessage("更新省份失败！");
        }

        return result;
    }

    //通过id查询一行
    @GetMapping("queryById/{id}")
    public Province queryById(@PathVariable Integer id){
        Province province = provinceServiceImpl.queryById(id);

        return province;
    }

    //删除
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result result = new Result();

        try {

            //可以判断一下省份中是否有 地点 place,即判断 place_counts 是否大于0
            //在service中做业务判断
            provinceServiceImpl.delete(id);
            result.setState(true).setMessage("删除省份成功");

        } catch (Exception e) {
            result.setState(false).setMessage("删除失败！");
        }

            return result;
    }


    //保存
    @PostMapping("save")
    public Result save(@RequestBody Province province){
        Result result = new Result();

        int save = provinceServiceImpl.save(province);
        if(save > 0){
            result.setState(true).setMessage("保存成功~");
        }else{
            result.setState(false).setMessage("保存失败！");
        }

        return result;
    }

    //查询所有
    @GetMapping("queryAll")
    public List<Province> queryAll(){

        return provinceServiceImpl.queryAll();
    }

    //分页查询
    @GetMapping("queryByPage")
    public Pagination queryByPage(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                                  @RequestParam(value = "pageSize",defaultValue = "4") Integer pageSize){
        //currentPage = currentPage== null?1:currentPage;
        //pageSize = pageSize==null?4:pageSize;


        Pagination page = provinceServiceImpl.queryByPage(currentPage,pageSize);



        return page;
    }


}
