package cn.cao.travel.service;

import cn.cao.travel.entity.Pagination;
import cn.cao.travel.entity.Province;

import java.util.List;

/**
 * @author cao
 * @create 2020/9/1 - 16:30
 */
public interface ProvinceService {

    int save(Province province);

    int update(Province province);

    int delete(Integer id);

    Province queryById(Integer id);

    List<Province> queryAll();

    //分页查询，当前页，每页显示个数,返回分页的实体对象
    Pagination queryByPage(Integer currentPage, Integer pageSize);

    //查询总条数
    Integer queryTotals();
}
