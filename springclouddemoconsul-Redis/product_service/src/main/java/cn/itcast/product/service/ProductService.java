package cn.itcast.product.service;

import cn.itcast.product.entity.Product;

public interface ProductService {

	/**
	 * 根据id查询
	 */
	Product findById(int orderNo);
	/**
	 * 保存
	 */
	Product save(Product product);
	/**
	 * 更新
	 */
	void update(Product product);
	/**
	 * 删除
	 */
	void delete(int orderNo);
}
