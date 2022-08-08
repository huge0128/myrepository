package org.wrh.product.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wrh.product.entity.Product;
import org.wrh.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
    public Product findByid(@PathVariable int orderNo) {
        Product product = productService.findById(orderNo);
        return product;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Product addOrder(Product product) {
        productService.save(product);
        return product;
    }
}
