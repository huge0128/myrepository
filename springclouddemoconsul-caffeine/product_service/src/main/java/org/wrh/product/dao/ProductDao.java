package org.wrh.product.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.wrh.product.entity.Product;

/**
 * 接口继承
 */
//public interface ProductDao extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
public interface ProductDao extends JpaRepository<Product,Integer>, JpaSpecificationExecutor<Product> {

}
