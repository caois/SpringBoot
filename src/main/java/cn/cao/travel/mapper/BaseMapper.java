package cn.cao.travel.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * 基础mapper 负责增删改查
 * @param <O> 查询的对象
 * @param <F> SQL中某个字段Field
 */
public interface BaseMapper<O,F> {

    int save(O o);

    int update(O o);

    int delete(F f);

    List<O> queryAll();

    //分页查询，从 startRow开始行查询，查询 rows 个数据
    List<O> queryByPage(@Param("startRow") Integer startRow, @Param("rows") Integer rows);
}
