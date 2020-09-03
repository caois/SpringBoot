package cn.cao.travel.mapper;

import cn.cao.travel.entity.Place;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cao
 * @create 2020/9/1 - 22:00
 */
public interface PlaceMapper {

    //根据省份ID分页查询
    List<Place> queryByProvinceIdPage(@Param("startRow") Integer startRow,
                                      @Param("rows") Integer rows,@Param("provinceId") Integer provinceId);

    //根据景点ID删除景点
    int deleteByPlace(Integer id);

    //根据省份ID 查询省份个数
    int queryByProvinceIdCounts(Integer provinceId);

    //根据 景点id查询景点
    Place queryPlaceById(Integer id);

    //保存
    void save(Place place);

    //更新景点
    void updatePlace(Place place);

    //根据 省份ID 删除景点
    void deleteByProvinceId(Integer  provinceId);

    //根据省份 id 查询所有所属景点
    List<Place> queryByProvinceId(Integer provinceId);
}
