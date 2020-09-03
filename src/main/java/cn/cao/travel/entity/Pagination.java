package cn.cao.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author cao
 * @create 2020/9/1 - 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Pagination implements Serializable {
    //当前页
    private Integer currentPage;
    //每页显示条数
    private Integer pageSize;
    //总页数
    private Integer totalPage;

    //分页对象
    private List<?> pageObject;
}
