package com.wrh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private Integer id;
    private String txNo;
    private String txAddress;
    private Double totalPrice;
    private List<Transaction> transactionList;
}
