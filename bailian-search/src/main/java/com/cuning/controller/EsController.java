package com.cuning.controller;

import com.alibaba.fastjson.JSON;
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.mapper.EsMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created On : 2022/6/12.
 * <p>
 * Author     : WangDeFeng
 * <p>
 * Description: EsController
 */
@RestController
@Slf4j
@Api(tags = "查询操作入口")
public class EsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private EsMapper esMapper;

    @GetMapping("/bulkRequest")
    @ApiOperation(value = "es导入商品")
    public void bulkRequest() throws IOException {

        //1.查询所有数据，mysql
        List<BailianGoodsInfo> goodsList = esMapper.selectList(null);

        //2.bulk批量导入对象
        BulkRequest bulkRequest= new BulkRequest();

        //2.1 循环goodsList，创建IndexRequest添加数据
        for (BailianGoodsInfo goods : goodsList) {



            String jsonString = JSON.toJSONString(goods);

            //新增添加数据对象                                                                          //记得给他指定你传入的格式
            IndexRequest indexRequest = new IndexRequest("goods_index").id(goods.getGoodsId() + "").source(jsonString, XContentType.JSON);
            //批量存入
            bulkRequest.add(indexRequest);
        }
        //3 执行索引对象 批量存入ES
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status()); //返回结果 ok
    }

    @GetMapping("/selectGoods")
    @ApiOperation(value = "查询商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "商品名",paramType = "query")
    })
    public String testSearch(String key) throws IOException {
        // 1.创建查询请求对象
        SearchRequest searchRequest = new SearchRequest("goods_index");
        // 2.构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // (1)查询条件 使用QueryBuilders工具类创建
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("goodsName", key);
        //        // 匹配查询
        //        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // (2)其他<可有可无>：（可以参考 SearchSourceBuilder 的字段部分）
        // 设置高亮
        searchSourceBuilder.highlighter(new HighlightBuilder());
        //        // 分页
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(3);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // (3)条件投入
        searchSourceBuilder.query(matchQueryBuilder);
        // 3.添加条件到请求
        searchRequest.source(searchSourceBuilder);
        // 4.客户端查询请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 5.查看返回结果
        SearchHits hits = search.getHits();
        for (SearchHit documentFields : hits.getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
        return  JSON.toJSONString(hits);
        // System.out.println(JSON.toJSONString(hits));

    }

}

