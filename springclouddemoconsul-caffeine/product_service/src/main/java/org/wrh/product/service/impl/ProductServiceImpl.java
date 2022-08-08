package org.wrh.product.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.wrh.product.dao.ProductDao;
import org.wrh.product.entity.Product;
import org.wrh.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Cacheable(value = "orderCaffeineCache", key = "#orderNo", unless = "#result == null ")
    public Product findById(int orderNo) {
        return productDao.findById(orderNo).get();
    }

    @Override
    @CachePut(value = "orderCaffeineCache", key = "#result.orderNo")
    public Product save(Product product) {
        productDao.save(product);
        return productDao.findById(product.getOrderNo()).get();
    }

    @Override
    public void update(Product product) {
        productDao.save(product);
    }

    @Override
    public void delete(int orderNo) {
        productDao.deleteById(orderNo);
    }

}
