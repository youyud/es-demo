package com.youyud.demo;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongqiuwu
 * @desciption
 * @date 2019/03/02 21:57
 */
@RestController
public class EsTest {
    @Autowired
    private TransportClient transportClient;

    private final String index = "geo_hash";
    private final String type = "_doc";

    @GetMapping("/creatEs")
    public void creatEs() {

        XContentBuilder source = null;
        try {
            source = XContentFactory.jsonBuilder();
            source.startObject()
                    .field("123", "456")
                    .field("789", "000")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        IndexResponse indexResponse = transportClient.prepareIndex(index, type, "3")
                .setSource(source).get();
        System.out.println(indexResponse);
    }

    @GetMapping("/queryIndex")
    public Map queryIndex() {
        GetResponse response = transportClient.prepareGet(index, type, "3").get();
        Map<String, Object> source = response.getSource();

        System.out.println("哈哈哈哈哈12345");
        System.out.println(source);
        return source;
    }

    @GetMapping("/updateIndex")
    public Map updateIndex() {
        XContentBuilder source = null;
        try {
            source = XContentFactory.jsonBuilder();
            source.startObject()
                    .field("123", "456")
                    .field("789", "111")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdateResponse response = transportClient.prepareUpdate(index, type, "3").setDoc(source).get();

        System.out.println(response);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("data",response);
        return map;
    }
}
