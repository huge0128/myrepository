package cn.itcast.product.controller;

import cn.itcast.product.entity.Product;
import cn.itcast.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;


	@RequestMapping(value = "/{orderNo}",method = RequestMethod.GET)
//	@Cacheable(value = "orderCache", key = "#orderNo", unless = "#result == null")
//	@Cacheable  在方法执行前，spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；若没有数据，调用方法并将方法返回值放到缓存中
	public Product findById(@PathVariable int orderNo) {
		Product product = productService.findById(orderNo);
		return product;
	}

	@RequestMapping(value = "add",method = RequestMethod.POST)
	public Product addOrder(Product product) {
		productService.save(product);
		return product;
	}
}
