package com.nft.service.impl;

import com.alibaba.fastjson.JSON;
import com.nft.entity.vo.SimpleGoods;
import com.nft.service.ESService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ESServiceImpl implements ESService {

    @Autowired
    @Qualifier("esRestClient")
    RestHighLevelClient client;

    @Value("${elasticsearch.indexname}")
    private String indexname;

    @Override
    public Map<String,Object> searchBlogForQuery(int pageNum, int pageSize, String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indexname);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //构造检索条件
        //分词查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.queryStringQuery(query).field("art.artAuthor"));
        boolQueryBuilder.should(QueryBuilders.queryStringQuery(query).field("art.artName"));
        boolQueryBuilder.should(QueryBuilders.queryStringQuery(query).field("art.artIntroduction"));
        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.timeout(new TimeValue(2));
//        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("art.artAuthor")
//                        .preTags("<span style='background-color:#00ff90; font-weight:bold;'>")
//                        .postTags("</span>");
//
//        highlightBuilder.field("art.artName")
//                        .preTags("<span style='background-color:#00ff90; font-weight:bold;'>")
//                        .postTags("</span>");
//
//        highlightBuilder.field("art.artIntroduction")
//                        .preTags("<span style='background-color:#00ff90; font-weight:bold;'>")
//                        .postTags("</span>");
//        searchSourceBuilder.highlighter(highlightBuilder);

        //分页：(当前页码-1)*每页显示的数据条数
        searchSourceBuilder.from((pageNum-1)*pageSize);
        searchSourceBuilder.size(pageSize);
        //想es发出请求
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        //处理响应结果
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        List<SimpleGoods> goods = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            //将响应的查询命中结果分装成dto对象
            SimpleGoods good = JSON.parseObject(hit.getSourceAsString(), SimpleGoods.class);
            //将高亮解析，并与原内容进行替换
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField titleField = highlightFields.get("artName");
            if (titleField != null){
                Text[] titleFragments = titleField.fragments();
                String title = "";
                for(Text text : titleFragments){
                    title += text;
                }
                good.getArt().setArtName(title);
            }
            HighlightField descriptionField = highlightFields.get("description");
            if (descriptionField != null){
                Text[] descriptionFragments = descriptionField.fragments();
                String description = "";
                for(Text text : descriptionFragments){
                    description += text;
                }
                good.getArt().setArtIntroduction(description);
            }
            goods.add(good);
        }

        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put("total", hits.getTotalHits().value);
        map.put("arts", goods);
        map.put("pageNum", pageNum);
        map.put("totalPages", (hits.getTotalHits().value + pageSize -1)/pageSize);
        return map;
    }
}
