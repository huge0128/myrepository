package cn.itcast.order.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品实体类
 */
@Data
public class Product implements Serializable {
	private int orderNo;
	private String rspCode;
	private String rspMsg;
	private String dataTime;
	private String data;
	private String bankSerialNo;
	private String orderAmount;
	private String fee;
	private String orderStatus;
}
