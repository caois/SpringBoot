package cn.cao.travel.service;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Place;

/**
 * @author cao
 * @create 2020/9/1 - 22:38
 */
public interface PlaceService {



    //根据省份ID分页查询
    Pagination queryByProvinceIdPage(Integer currentPage, Integer pageSize, Integer provinceId);

    //根据景点ID删除景点
    int deleteByPlace(Integer id);

    //根据省份ID 查询省份个数
    int queryByProvinceIdCounts(Integer provinceId);


    //根据 景点id查询景点
    Place queryPlaceById(Integer id);

    //保存
    void save(Place place);

    void updatePlace(Place place);


}
