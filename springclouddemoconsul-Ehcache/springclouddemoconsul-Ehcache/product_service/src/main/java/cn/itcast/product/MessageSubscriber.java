package cn.itcast.product;

import cn.itcast.product.aspect.CacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);

    @Autowired
    private CacheFactory cacheFactory;

    
}
