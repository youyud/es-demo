package com.youyud.demo.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhongqiuwu
 * @desciption
 * @date 2019/03/02 20:02
 */
@Service
@Slf4j
public class EsSearchServiceImpl implements EsSearchService {
    @Autowired
    private QueryFactory factory;

    @Autowired
    private TransportClient transportClient;

    /**
     * 查找位于多边形中的位置
     *
     * @param index  索引
     * @param type   类型
     * @param field  索引字段
     * @param points 构成多边形的点
     */
    @Override
    public List<Map<String, Object>> searchGeoPolygon(String index, String type, String field, List<GeoPoint> points) throws Exception {
        SearchSourceBuilder builder = factory.builtPolygonQuery(field, points);
        return search(index, type, builder);
    }

    /**
     * 查询矩形范围内的数据
     *
     * @param index        index
     * @param type         type
     * @param field        索引字段
     * @param bottom_right 右上坐标
     * @param top_left     左下坐标
     */
    @Override
    public List<Map<String, Object>> searchGeoBoundingBox(String index, String type, String field, GeoPoint bottom_right, GeoPoint top_left) throws Exception {
        SearchSourceBuilder builder = factory.builtBoundingBoxQuery(field, bottom_right, top_left);
        return search(index, type, builder);
    }

    /**
     * 查询距离中心点指定的范围内的位置
     *
     * @param index    index
     * @param type     type
     * @param field    索引字段
     * @param distance 距离
     * @param point    中心点
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> searchGeoDistance(String index, String type, String field, String distance, GeoPoint point) throws Exception {
        SearchSourceBuilder builder = factory.builtDistanceQuery(field, distance, point);
        return search(index, type, builder);
    }

    /**
     * 执行查询
     *
     * @param index   索引
     * @param type    type
     * @param builder 查询语句
     * @return List<Map>
     */
    private List<Map<String, Object>> search(String index, String type, SearchSourceBuilder builder) {
        try {
            List<Map<String, Object>> list = new ArrayList<>();
            SearchRequestBuilder srb = transportClient.prepareSearch(index);
            if (type != null && type.length() != 0) {
                srb.setTypes(type);
            }
            srb.setSource(builder);
            SearchResponse searchResponse = srb.execute().actionGet();
            SearchHits hits = searchResponse.getHits();
            long time = searchResponse.getTookInMillis() / 1000;
            log.info("query result size:" + hits.totalHits + ",spend time:" + time + "s");
            for (SearchHit hit : hits) {
                Map<String, Object> map = hit.getSource();
                //获取distance数据时，获取距离具体值
                if (hit.getSortValues().length != 0) {
                    BigDecimal geoDis = new BigDecimal((Double) hit.getSortValues()[0]);
                    map.put("distance", geoDis.setScale(4, BigDecimal.ROUND_HALF_DOWN) + "km");
                }
                list.add(map);
                log.info("hits:" + map);
            }
            return list;
        } catch (Exception e) {
            log.error("error!", e);
        }
        return null;
    }
}
