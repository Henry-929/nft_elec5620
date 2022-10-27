package com.nft.aspect;

import com.alibaba.fastjson.JSON;
import com.nft.entity.vo.SimpleGoods;
import com.nft.service.GoodsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Aspect
@Component
public class ElasticSearchAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("esRestClient")
    RestHighLevelClient client;
    @Autowired
    GoodsService goodsService;

    @Value("${elasticsearch.indexname}")
    private String indexname;

    @Pointcut("execution(* com.nft.service.impl.ArtServiceImpl.setSell(..))")
    public void searchSAAndUPBlog(){}

    @Pointcut("execution(* com.nft.service.impl.GoodsServiceImpl.setNotSell(..)) || "
            + "execution(* com.nft.service.impl.ShoppingCarServiceImpl.buyGoodsById(..))")
    public void searchDEBlog(){}


    @Before("searchSAAndUPBlog()")
    public void doBefore(){
                logger.info("------------save and update doBefore---------------");
    }

    @After("searchSAAndUPBlog()")
    public void doAfter(JoinPoint joinPoint) throws IOException {
        Object[] args = joinPoint.getArgs();
        if (args.length != 0) {
            esSearchInput();
        }
        logger.info("------------save and update doAfter---------------");
    }

    @Before("searchDEBlog()")
    public void doDeleteBefore(){
        logger.info("------------delete doBefore---------------");
    }

    @After("searchDEBlog()")
    public void doDeleteAfter(JoinPoint joinPoint) throws IOException {
        Object[] args = joinPoint.getArgs();
        Long goodid = (Long) args[0];
        if (args.length != 0) {
            esSearchDelete(goodid);
        }
        logger.info("------------delete doAfter---------------");
    }

    /*
    * 从数据库中获取批量数据，然后批量插入elasticsearch中
    * */
    private void esSearchInput() throws IOException {
        List<SimpleGoods> goods = goodsService.getAllGoodNoLimit();
        BulkRequest request = new BulkRequest();
        request.timeout("5m");
        for (SimpleGoods good : goods){
            String jsonString = JSON.toJSONString(good);
            request.add(new IndexRequest(indexname).id(good.getGoodsId().toString())
                    .source(jsonString, XContentType.JSON));
        }

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        logger.info(response.hasFailures()?"上传elasticsearch失败":"上传elasticsearch成功，耗时："+response.getTook());
    }

    /*
    * 根据blog id删除elasticsearch中的记录
    * */
    private void esSearchDelete(Long id) throws IOException {
        DeleteRequest request = new DeleteRequest(indexname,id.toString());
        client.delete(request, RequestOptions.DEFAULT);
    }
}
