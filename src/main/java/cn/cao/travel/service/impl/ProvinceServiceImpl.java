package cn.cao.travel.service.impl;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Place;
import cn.cao.travel.entity.Province;
import cn.cao.travel.mapper.PlaceMapper;
import cn.cao.travel.mapper.ProvinceMapper;
import cn.cao.travel.service.ProvinceService;
import cn.cao.travel.utils.ImageFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cao
 * @create 2020/9/1 - 16:33
 */
@Service
@Transactional
public class ProvinceServiceImpl implements ProvinceService {

    @Resource
    private ProvinceMapper provinceMapper;

    @Resource
    private PlaceMapper placeMapper;

    @Value("${upload.dir}")
    private String realPath;

    @Override
    public int save(Province province) {
        return provinceMapper.save(province.setPlaceCounts(0));
    }

    @Override
    public int update(Province province) {
        System.out.println("province = " + province);
        return provinceMapper.update(province);
    }

    @Override
    public int delete(Integer id) {
        //还要将本地图片先删除
        List<Place> places = placeMapper.queryByProvinceId(id);

        List<String> imageAbsolutePath = new ArrayList<>();
        //获取所有图片的绝对路径
        places.forEach(place ->  imageAbsolutePath.add(realPath + place.getPicPath()) );
        //删除图片
        ImageFileUtils.deleteFiles(imageAbsolutePath);


        //根据省份 ID 去查询 place，将省份下所属景点也删除
        placeMapper.deleteByProvinceId(id);

        //增删改其实不用返回值的，但是有时候又需要这个返回值
        int delete = provinceMapper.delete(id);


        return delete;
    }

    @Override
    public Province queryById(Integer id) {

        return provinceMapper.queryById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Province> queryAll() {

        return provinceMapper.queryAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Pagination queryByPage(Integer currentPage, Integer pageSize) {
        Pagination page = new Pagination();
        //分页查询，数据库需要的参数是：起始行数:startRow，查询多少行数:rows
        //提供参数为；当前页currentPage，每页显示currentPage

        Integer startRow = (currentPage-1)*pageSize;   //起始行数

        //每页显示的数量，等于数据库查询行数Integer rows = pageSize;查询行数

        Integer totals = queryTotals();//总条数
        Integer totalPage = totals%pageSize == 0?totals/pageSize:totals/pageSize +1;

        List<Province> provinces = provinceMapper.queryByPage(startRow,pageSize);



        //设置分页对象属性
        page.setPageObject(provinces).setCurrentPage(currentPage)
                .setPageSize(pageSize).setTotalPage(totalPage);
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer queryTotals() {

        return provinceMapper.queryTotals();
    }
}
