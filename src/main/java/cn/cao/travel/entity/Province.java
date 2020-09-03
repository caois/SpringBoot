package cn.cao.travel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author cao
 * @create 2020/8/31 - 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Province implements Serializable {

    private Integer id;
    private String name;
    private String tags;
    private Integer placeCounts;

    //省份下的所有景点
    private List<Place> places;
}
