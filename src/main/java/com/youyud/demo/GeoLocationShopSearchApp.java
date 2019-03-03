//package com.youyud.demo;
//
//import com.google.gson.Gson;
//import org.assertj.core.util.Lists;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.geo.ShapeRelation;
//import org.elasticsearch.common.geo.builders.ShapeBuilders;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.index.query.GeoShapeQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.util.ArrayList;
//import java.util.Map;
//
//import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;
//
///**
// * @author zhongqiuwu
// * @desciption
// * @date 2019/03/02 20:10
// */
//public class GeoLocationShopSearchApp {
//
//    @SuppressWarnings({ "resource", "unchecked" })
//    public static void main(String[] args) throws Exception {
//        Settings settings = Settings.builder()
//                .put("cluster.name", "elasticsearch")
//                .build();
//
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
//        ;
//        Coordinate coordinate = new Coordinate(100.3, 0.3);
//        GeoShapeQueryBuilder qb = null;
//        try {
//            qb = geoShapeQuery(
//                    "0",
//                    ShapeBuilders.newPoint(coordinate));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        qb.relation(ShapeRelation.INTERSECTS);
//        SearchResponse response = client.prepareSearch("example")
//                .setTypes("doc")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setQuery(qb)
//                .setSize(200)
//                .setFrom(0)
//                .execute()
//                .actionGet();
//        if (response != null && response.getHits() != null && response.getHits().getHits()!=null) {
//
//            final ArrayList<SearchHit> searchHits = Lists.newArrayList(response.getHits().getHits());
//            searchHits.forEach(GeoLocationShopSearchApp::printHitsSource);
//        }
//        client.close();
//    }
//
//    public static void printHitsSource(SearchHit searchHitFields){
//        Map<String, Object> source = searchHitFields.getSourceAsMap();
//        System.out.println(new Gson().toJson(source));
//    }
//}
