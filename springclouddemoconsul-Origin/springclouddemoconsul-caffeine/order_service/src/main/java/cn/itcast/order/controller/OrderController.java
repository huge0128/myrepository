package cn.itcast.order.controller;

import cn.itcast.order.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/get/{orderNo}", method = RequestMethod.GET)
	public Product findById(@PathVariable int orderNo) {
		String url = "http://service-product1/product/" + orderNo;
		System.out.println(url);
		Product product = restTemplate.getForObject(url + orderNo,Product.class);
		return product;
	}

}
