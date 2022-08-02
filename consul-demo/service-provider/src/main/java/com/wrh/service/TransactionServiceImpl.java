package com.wrh.service;

import com.wrh.pojo.Transaction;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    // 一个支持缓存的方法在对象内部调用不会触发缓存
    // Spring的EL表达式来指定key
    @Cacheable (value = "HelloWorldCache", key= "#param")
    public  String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }

    @Cacheable (value = "HelloWorldCache", key = "#key")
    public Transaction findById(String txId) {
        System.out.println("TXCache:" + txId);
        return new Transaction(1, 999.99, "wrh", 20);
    }

    public int insertTransaction(Integer id, Double balance, String xxxx, Integer num) {

    }
}
