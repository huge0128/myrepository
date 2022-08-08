package cn.itcast.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
@EntityScan("cn.itcast.product.entity")
public class ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class,args);
	}
}
