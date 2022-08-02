package com.wrh;


import com.wrh.pojo.Transaction;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheTest {
    public static void main(String[] args) {
        // 创建缓存管理器
        CacheManager cacheManager = CacheManager.create("./service-provider/src/main/resources/ehcache.xml");

        // 获取缓存对象
        Cache cache = cacheManager.getCache("HelloWorldCache");

        // 创建元素
        Element element = new Element("key1", "value1");

        // 将元素添加到缓存
        cache.put(element);

        // 获取缓存
        Element value = cache.get("key1");
        System.out.println("val" + value);
        System.out.println(value.getObjectValue());

        // 删除元素
        cache.remove("key1");

        Transaction transaction = new Transaction(25,9999.111,"ahsvdaisjkh", 10);
        Element element1 = new Element("transaction", transaction);
        cache.put(element1);
        Element value1 = cache.get("transaction");
        System.out.println("val1" + value1);

        Transaction transaction1 = (Transaction) value1.getObjectValue();
        System.out.println(transaction1);
        System.out.println("===============");
        System.out.println(cache.getSize());

        // 刷新缓存
        cache.flush();

        // 关闭缓存管理器
        cacheManager.shutdown();
    }
}
