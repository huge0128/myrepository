package cn.itcast.product.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiCacheConfig {
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        // 缓存过期时间
        Map<String, Long> expires = new HashMap<>();
        expires.put("ExpOpState", 0L);
        expires.put("ImpOpState", 0L);
        rcm.setExpires(expires);
        rcm.setDefaultExpiration(600);
        return rcm;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        template.setValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * redis 消息监听器
     * 可添加多个监听器监听不同话题，将消息监听器和相应的消息订阅处理器绑定，
     * 消息监听器通过反射技术调用消息订阅器的相关方法进行一些业务处理
     * @Param connectionfactory
     * @Param listenerAdapter
     * @return
     */

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 订阅redis.uncache通道
        container.addMessageListener(listenerAdapter, new PatternTopic("redis.uncache"));
        // container 可以添加多个messagelistener
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(MessageSubscriber receiver) {
        //
        return new MessageListenerAdapter(receiver, "handle");
    }
}
