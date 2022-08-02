package com.wrh.service;

import com.wrh.pojo.Transaction;

import java.util.List;

/*交易服务*/
public interface TransactionService {
    /*查询交易*/

//    public List<Transaction> selectTransactionList();
    public Transaction findById(String txId);

    public String getTimestamp(String param);

    public int addTransaction(Integer id,
    Double balance,
    String xxxx,
    Integer num);
}
