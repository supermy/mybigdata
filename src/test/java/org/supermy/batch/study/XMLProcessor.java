package org.supermy.batch.study;


import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * XML文件处理类。
 */
@Component("xmlProcessor")
public class XMLProcessor implements ItemProcessor<Goods, Goods> {

    /**
     * XML文件内容处理。
     * 
     */
    public Goods process(Goods goods) throws Exception {
    	System.out.println(">>>>>>>>>>>>>>process xml");
        // 购入日期变更
        goods.setBuyDay(new Date());
        // 顾客信息变更
        goods.setCustomer(goods.getCustomer() + "顾客!");
        // ISIN变更
        goods.setIsin(goods.getIsin() + "IsIn");
        // 价格变更
        goods.setPrice(goods.getPrice() + 1000.112);
        // 数量变更
        goods.setQuantity(goods.getQuantity() + 100);
        // 处理后的数据返回
        return goods;
    }
}