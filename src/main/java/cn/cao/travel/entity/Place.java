package cn.cao.travel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cao
 * @create 2020/8/31 - 16:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Place implements Serializable {

    private Integer id;
    private String name;
    private String picPath;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date peakSeasonTime;

    private String peakSeasonTicket;
    private String offSeasonTicket;
    private String introduction;

    private Integer provinceId;


}
