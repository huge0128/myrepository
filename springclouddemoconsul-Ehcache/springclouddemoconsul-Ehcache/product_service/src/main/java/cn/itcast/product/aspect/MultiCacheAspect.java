package cn.itcast.product.aspect;


import cn.itcast.product.annotation.CacheEvict1;
import cn.itcast.product.annotation.Cacheable1;
import cn.itcast.product.utils.JsonUtil;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MultiCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(MultiCacheAspect.class);

    @Autowired
    private CacheFactory cacheFactory;

    // 通过容器初始化监听器，根据外部配置的@EnableCaching注解控制缓存开关
    private boolean cacheEnable;

    @Pointcut("@annotation(cn.itcast.product.annotation.Cacheable1)")
    public void cacheableAspect() {

    }

    @Pointcut("@annotation(cn.itcast.product.annotation.CacheEvict1)")
    public void cacheEvict() {}

    @Around("cacheableAspect()")
    public Object cache(ProceedingJoinPoint joinPoint) {

        // 获取被切面修饰的方法的参数列表
        Object[] args = joinPoint.getArgs();
        // result是方法的最终返回结果
        Object result = null;
        // 未开启缓存，直接调用处理方法返回
        if(!cacheEnable) {
            try {
                // 执行目标方法，获取方法返回值
                result = joinPoint.proceed(args);
            } catch (Throwable e) {
                logger.error("",e);
            }
            return result;
        }

        // 获取被代理方法的返回值类型
        Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();

        // 获取被代理方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        // 得到被代理的方法的注解
        Cacheable1 cacheable1 = method.getAnnotation(Cacheable1.class);
        // 获取经过el解析后的key
        String key = parserKey(cacheable1.key(), method, args);
        Class<?> elementClass = cacheable1.type();
        // 从注解获取缓存名称
        String name = cacheable1.value();


        try {
            // 从一级缓存中读取数据 如Ehcache
            String cacheValue = cacheFactory.ehGet(name, key);
            if(StringUtils.isEmpty(cacheValue)) {
                // 如果一级缓存中没有数据，从二级缓存中取数据
                cacheValue = cacheFactory.redisGet(name, key);
                if(StringUtils.isEmpty(cacheValue)) {
                    // 如果二级缓存中没有数据，调用业务方法获取结果
                    result = joinPoint.proceed(args);
                    // 将结果序列化后放入二级缓存
                    cacheFactory.redisPut(name, key, serialize(result));
                } else {
                    // 如果二级缓存中有数据，将缓存中的数据反序列化返回
                    if(elementClass == Exception.class) {
                        result = deserialize(cacheValue, returnType);
                    } else {
                        result = deserialize(cacheValue, returnType, elementClass);
                    }
                }

            }

        } catch(Throwable throwable) {
            logger.error("", throwable);
        }

        return result;
    }

    /**
     * 方法调用前清除缓存，然后调用业务方法
     * @param joinpoint
     * @return
     * @throws Throwable
     */

    @Around("cacheEvict()")
    public Object evictCache(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取被代理方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取被切面修饰的方法的参数列表
        Object[] args = joinPoint.getArgs();
        // 获取被代理方法的注解
        CacheEvict1 cacheEvict1 = method.getAnnotation(CacheEvict1.class);
        // 获得经过el解析后的key
        String key = parseKey(cacheEvict1.key(), method, args);
        // 从注解中获取缓存
        String name = cacheEvict1.value();
        // 清除对应缓存
        cacheFactory.cacheDel(name, key);
        return joinPoint.proceed(args);
    }

    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     * @return
     */
    private String parseKey(String key, Method method, Object[] args) {

        if (StringUtils.isEmpty(key)) return null;

        // 获取被拦截方法参数名列表（使用spring支持类库）
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文
        for(int i=0; i<paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

    // 序列化
    private String serialize(Object obj) {

        String result = null;
        try {
            result = JsonUtil.serialize(obj);
        } catch (Exception e) {
            result = obj.toString();
        }
        return  result;
    }

    // 反序列化
    private Object deserialize(String str, Class clazz) {

        Object result = null;
        try {
            if (clazz == JSONObject.class) {
                result = new JSONObject(str);
            } else if (clazz == JSONArray.class) {
                result = new JSONArray(str);
            } else {
                result = JsonUtil.deserialize(str, clazz);
            }
        } catch (Exception e) {

        }
        return result;
    }

    //反序列化,支持List<xxx>
    private Object deserialize(String str,Class clazz,Class elementClass) {

        Object result = null;
        try {
            if(clazz == JSONObject.class) {
                result = new JSONObject(str);
            } else if(clazz == JSONArray.class) {
                result = new JSONArray(str);
            } else {
                result = JsonUtil.deserialize(str,clazz,elementClass);
            }
        } catch(Exception e) {
        }
        return result;

    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }

}
