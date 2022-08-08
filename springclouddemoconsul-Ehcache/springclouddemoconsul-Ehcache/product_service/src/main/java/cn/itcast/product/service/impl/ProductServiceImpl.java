package cn.itcast.product.service.impl;

import cn.itcast.product.dao.ProductDao;
import cn.itcast.product.entity.Product;
import cn.itcast.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	@Cacheable(value = "orderEhcacheCache", key = "#orderNo", unless = "#result == null")
	public Product findById(int orderNo) {
		return productDao.findById(orderNo).get();
	}

	@Override
	@CachePut(value = "orderEhcacheCache", key = "#result.orderNo")
	public Product save(Product product) {
		productDao.save(product);
		return productDao.findById(product.getOrderNo()).get();
	}

	@Override
	public void update(Product product) {
		productDao.save(product);
	}

	@Override
	public void delete(int orderNO) {
		productDao.deleteById(orderNO);
	}
}
