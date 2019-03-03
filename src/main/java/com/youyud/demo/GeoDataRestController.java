//package com.youyud.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.common.geo.GeoPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Logger;
//
//@RestController
//@RequestMapping("/geo")
//@Slf4j
//public class GeoDataRestController {
//
//    @Autowired
//    private EsSearchService service;
//
//
//    @RequestMapping(value = "/distance",method = RequestMethod.POST)
//    public List<Map<String,Object>> searchGeoWithDistance(@RequestBody RequestBean bean,
//                                                          @Param("distance") String distance){
//        Map<String,GeoPoint> map = bean.getPoints();
//        GeoPoint point = map.get("point");
//        if (distance == null || point == null){
//            log.error("request param error!");
//            return null;
//        }
//        List<Map<String,Object>> result= null;
//        try {
//            result = service.searchGeoDistance(bean.getIndex(),bean.getType(),bean.getField(),distance,point);
//        }catch (Exception e){
//            log.error("query error!",e);
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "/bounding",method = RequestMethod.POST)
//    public List<Map<String,Object>> searchGeoWithBoundingBox(@RequestBody RequestBean bean){
//        Map<String,GeoPoint> map = bean.getPoints();
//        GeoPoint bottom_point = map.get("bottom_right");
//        GeoPoint top_left = map.get("top_left");
//        if (bottom_point == null || top_left == null){
//            log.error("request param error!");
//            return null;
//        }
//        List<Map<String,Object>> result= null;
//        try {
//            result = service.searchGeoBoundingBox(bean.getIndex(),bean.getType(),bean.getField(),bottom_point,top_left);
//        }catch (Exception e){
//            log.error("query error!",e);
//        }
//        return result;
//    }
//
//    @RequestMapping(value = "/coordinate",method = RequestMethod.POST)
//    public List<Map<String,Object>> searchGeoWithCoordinate(@Param("index") String index,
//                                                            @Param("type") String type,
//                                                            @Param("field") String field,
//                                                            @RequestBody List<GeoPoint> points){
//        log.info("data:"+points);
//        List<Map<String,Object>> result= null;
//        try {
//            result = service.searchGeoPolygon(index,type,field,points);
//        }catch (Exception e){
//            log.error("query error!",e);
//        }
//        return result;
//    }
//}
