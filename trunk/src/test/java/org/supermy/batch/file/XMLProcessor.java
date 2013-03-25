package org.supermy.batch.file;

import org.springframework.batch.item.ItemProcessor;

public class XMLProcessor implements ItemProcessor<Trade, Trade> {

    /**
     * XML文件内容处理。
     * 
     */
    public Trade process(Trade trade) throws Exception {
    	System.out.println(">>>>>>>>>>>>>>>>>>>>:"+trade);
        return trade;
    }
}