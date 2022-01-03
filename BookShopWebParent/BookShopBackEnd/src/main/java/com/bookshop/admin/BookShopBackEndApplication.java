package com.bookshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.bookshop.common.entity","com.bookshop.admin.user"})
public class BookShopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShopBackEndApplication.class, args);
	}

}
