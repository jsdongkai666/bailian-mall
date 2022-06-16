package com.cuning.util;

import com.alibaba.fastjson.JSON;
import com.cuning.bean.BailianGoodsInfo;
import com.cuning.mapper.EsMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
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
@Component
@Slf4j
public class EsUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Autowired
    private EsMapper esMapper;

    /**
     * @author : wangdefeng
     * @date   : 2022/6/14
     * @param  : []
     * @return : void
     * @description : 删除数据库
     */
    public void deleteIndex() throws IOException {

        DeleteIndexRequest request = new DeleteIndexRequest("goods_index");
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());// 是否删除成功
        // restHighLevelClient.close();
    }

    /**
     * @author : wangdefeng
     * @date   : 2022/6/14
     * @param  : []
     * @return : void
     * @description : 测试数据库是否存在
     */
    public Boolean indexIsExists() throws IOException {
        GetIndexRequest request = new GetIndexRequest("goods_index");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        restHighLevelClient.close();
        return exists;

    }
    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [java.lang.Integer]
     * @return : void
     * @description : 删除数据
     */
    public void deleteBulk(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("goods_index",id);
        deleteRequest.timeout("1s");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());// ok
    }

    /**
     * @author : wangdefeng
     * @date   : 2022/6/14
     * @param  : [com.cuning.bean.BailianGoodsInfo]
     * @return : void
     * @description : 插入数据
     */
    public void insertBulk(BailianGoodsInfo bailianGoodsInfo) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<BailianGoodsInfo> users = new ArrayList<>();
        users.add(bailianGoodsInfo);
        // 批量请求处理
        for (int i = 0; i < users.size(); i++) {
            bulkRequest.add(
                    // 这里是数据信息
                    new IndexRequest("goods_index")
                            .id(users.get(i).getGoodsId())// 没有设置id 会自定生成一个随机id
                            .source(JSON.toJSONString(users.get(i)),XContentType.JSON)
            );
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk.status());// ok
    }
    /**
     * @author : wangdefeng
     * @date   : 2022/6/15
     * @param  : [com.cuning.bean.BailianGoodsInfo]
     * @return : void
     * @description : 更新数据
     */
    public void updateDocument(BailianGoodsInfo bailianGoodsInfo) throws IOException {
        UpdateRequest request = new UpdateRequest("goods_index", bailianGoodsInfo.getGoodsId());
        BailianGoodsInfo info = esMapper.selectById(bailianGoodsInfo.getGoodsId());
        log.info("--info:{}--",info);
        if(bailianGoodsInfo.getGoodsName()==null||bailianGoodsInfo.getGoodsName()==""){
            log.info("--GoodsName():{}--",bailianGoodsInfo.getGoodsName());
            bailianGoodsInfo.setGoodsName(info.getGoodsName());
            log.info("--GoodsName():{}--",bailianGoodsInfo.getGoodsName());
        }
        if(bailianGoodsInfo.getGoodsIntro()==null||bailianGoodsInfo.getGoodsIntro()==""){
            bailianGoodsInfo.setGoodsIntro(info.getGoodsIntro());
        }
        if(bailianGoodsInfo.getGoodsCategoryId()==0){
            bailianGoodsInfo.setGoodsCategoryId(info.getGoodsCategoryId());
        }
        if(bailianGoodsInfo.getGoodsCoverImg()==null||bailianGoodsInfo.getGoodsCoverImg()==""){
            bailianGoodsInfo.setGoodsCoverImg(info.getGoodsCoverImg());
        }
        if(bailianGoodsInfo.getGoodsCarousel()==null||bailianGoodsInfo.getGoodsCarousel()==""){
            bailianGoodsInfo.setGoodsCarousel(info.getGoodsCarousel());
        }
        if(bailianGoodsInfo.getOriginalPrice()==0){
            bailianGoodsInfo.setOriginalPrice(info.getOriginalPrice());
        }
        if(bailianGoodsInfo.getSellingPrice()==0){
            bailianGoodsInfo.setSellingPrice(info.getSellingPrice());
        }
        if(bailianGoodsInfo.getStockNum()==0){
            bailianGoodsInfo.setStockNum(info.getStockNum());
        }
        if(bailianGoodsInfo.getTag()==null||bailianGoodsInfo.getTag()==""){
            bailianGoodsInfo.setTag(info.getTag());
        }
        if(bailianGoodsInfo.getGoodsDetailContent()==null||bailianGoodsInfo.getGoodsDetailContent()==""){
            bailianGoodsInfo.setGoodsDetailContent(info.getGoodsDetailContent());
        }
        if(bailianGoodsInfo.getCreateUser()==0){
            bailianGoodsInfo.setCreateUser(info.getCreateUser());
        }
        if(bailianGoodsInfo.getUpdateUser()==0){
            bailianGoodsInfo.setUpdateUser(info.getUpdateUser());
        }
        request.doc(JSON.toJSONString(bailianGoodsInfo), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status()); // OK
        restHighLevelClient.close();
    }


    /**
     * @author : wangdefeng
     * @date   : 2022/6/14
     * @param  : []
     * @return : void
     * @description : 导入es商品
     */
    public void bulkRequest() throws IOException {

        //1.查询所有数据，mysql
        List<BailianGoodsInfo> goodsList = esMapper.selectList(null);

        //2.bulk批量导入对象
        BulkRequest bulkRequest= new BulkRequest();

        //2.1 循环goodsList，创建IndexRequest添加数据
        for (BailianGoodsInfo goods : goodsList) {
            String jsonString = JSON.toJSONString(goods);
            //新增添加数据对象                                                                          //记得给他指定你传入的格式
            IndexRequest indexRequest = new IndexRequest("goods_index").id(goods.getGoodsId()).source(jsonString, XContentType.JSON);
            //批量存入
            bulkRequest.add(indexRequest);
        }
        //3 执行索引对象 批量存入ES
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        bulkRequest.timeout("10s");
        System.out.println(response.status()); //返回结果 ok
    }


    /**
     * @author : wangdefeng
     * @date   : 2022/6/14
     * @param  : [java.lang.String, java.lang.Integer, java.lang.Integer]
     * @return : java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @description : 查询商品
     */
    public List<Map<String, Object>> search(String searchKey, Integer pageIndex, Integer pageSize) throws IOException {

        if (pageIndex < 0){
            pageIndex = 0;
        }

        // 1.创建查询请求对象
        SearchRequest searchRequest = new SearchRequest("goods_index");
        // 2.构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // (1)查询条件 使用QueryBuilders工具类创建
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("goodsName", searchKey);
        //        // 匹配查询
        //        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // (2)其他<可有可无>：（可以参考 SearchSourceBuilder 的字段部分）
        // 设置高亮
        searchSourceBuilder.highlighter(new HighlightBuilder());
        //        // 分页
        // searchSourceBuilder.from(pageIndex);
        // searchSourceBuilder.size(pageSize);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // (3)条件投入
        searchSourceBuilder.query(matchQueryBuilder);
        // 3.添加条件到请求
        searchRequest.source(searchSourceBuilder);
        // 4.客户端查询请求
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // restHighLevelClient.close();
        // 5.查看返回结果
        SearchHits hits = search.getHits();
        List<Map<String,Object>> results = new ArrayList<>();
        for (SearchHit documentFields : hits.getHits()) {
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();
            results.add(sourceAsMap);
        }
        return results;
        // System.out.println(JSON.toJSONString(hits));

    }

}

