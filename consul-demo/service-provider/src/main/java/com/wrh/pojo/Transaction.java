package com.wrh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {

    private Integer id;
    private Double balance;
    private String xxxx;
    private Integer num;
}
