package cn.itcast.product.listener;

import cn.itcast.product.aspect.MultiCacheAspect;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 判断根容器为Spring容器，防止出现调用两次的情况（mvc加载也会触发一次）
        if(event.getApplicationContext().getParent()==null) {
            //得到所有被@EnableCaching注解修饰的类
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(EnableCaching.class);
            if (beans != null && !beans.isEmpty()) {
                MultiCacheAspect multiCache = (MultiCacheAspect) event.getApplicationContext().getBean("multiCacheAspect");
                multiCache.setCacheEnable(true);
            }
        }
    }
}
