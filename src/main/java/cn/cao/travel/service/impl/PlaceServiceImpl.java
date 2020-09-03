package cn.cao.travel.service.impl;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Place;
import cn.cao.travel.entity.Province;
import cn.cao.travel.mapper.PlaceMapper;
import cn.cao.travel.mapper.ProvinceMapper;
import cn.cao.travel.service.PlaceService;
import cn.cao.travel.utils.ImageFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cao
 * @create 2020/9/1 - 22:39
 */
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Resource
    private PlaceMapper placeMapper;

    @Resource
    private ProvinceMapper provinceMapper;

    @Value("${upload.dir}")
    private String realPath;

    //保存景点
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Pagination queryByProvinceIdPage(Integer currentPage, Integer pageSize, Integer provinceId) {
        Pagination page = new Pagination();

        Integer startRow = (currentPage - 1) * pageSize;
        Integer totals = placeMapper.queryByProvinceIdCounts(provinceId);//总条数
        //计算总页数
        Integer totalPage = totals % pageSize ==0? totals/pageSize : totals/pageSize+1;

        List<Place> places = placeMapper.queryByProvinceIdPage(startRow, pageSize, provinceId);

        //设置分页对象属性
        page.setPageObject(places).setCurrentPage(currentPage)
                .setPageSize(pageSize).setTotalPage(totalPage);

        return page;
    }

    @Override
    public int deleteByPlace(Integer id) {

        //删除景点同时将本地图片删除了
        Place place = placeMapper.queryPlaceById(id);
        ImageFileUtils.deleteFile(realPath + place.getPicPath());

        return placeMapper.deleteByPlace(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int queryByProvinceIdCounts(Integer provinceId) {
        return placeMapper.queryByProvinceIdCounts(provinceId);
    }

    @Override
    public Place queryPlaceById(Integer id) {
        return placeMapper.queryPlaceById(id);
    }

    //保存
    @Override
    public void save(Place place) {

        //省份景点数需要+1
        //获取省份  id
        Integer provinceId = place.getProvinceId();
        //新增一个placeCounts运算方法调用一次SQL
        //保存景点
        placeMapper.save(place);
        //所属省景点+1
        provinceMapper.updatePlaceCounts(provinceId,1);


        /*//获取当前 place_counts--------->调用了三次数据库
        Province province = provinceMapper.queryById(provinceId);
        province.setPlaceCounts(province.getPlaceCounts()+1);
        provinceMapper.update(province);
        placeMapper.save(place);*/

    }
    //更新
    @Override
    public void updatePlace(Place place) {
        placeMapper.updatePlace(place);
    }
}
