package com.youyud.demo.es;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EsSearchService {

    List<Map<String, Object>> searchGeoPolygon(String index, String type, String field, List<GeoPoint> points) throws Exception;

    List<Map<String, Object>> searchGeoBoundingBox(String index, String type, String field, GeoPoint bottom_right, GeoPoint top_left) throws Exception;

    List<Map<String, Object>> searchGeoDistance(RequestBean bean, Pageable pageable) throws Exception;
}
