package cn.cao.travel.mapper;

import cn.cao.travel.entity.Province;
import org.apache.ibatis.annotations.Param;

/**
 * @author cao
 * @create 2020/9/1 - 16:16
 */
public interface ProvinceMapper extends BaseMapper<Province,Integer>{

    //查询总条数
    Integer queryTotals();

    //通过id查询一行
    Province queryById(Integer id);

    //根据省份ID， 景点数 增加 或删除
    void updatePlaceCounts(@Param("id") Integer id, @Param("number") Integer number);


}
