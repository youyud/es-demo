package com.youyud.demo.es;

import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;

/**
 * @author zhongqiuwu
 * @desciption
 * @date 2019/03/04 11:26
 */
@Data
public class RequestBean {
    private GeoPoint point;

    private String index;

    private String type;

    private String field;

    private String distance;
}
