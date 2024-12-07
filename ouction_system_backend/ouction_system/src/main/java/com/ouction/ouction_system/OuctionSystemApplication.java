package com.ouction.ouction_system;

import com.ouction.ouction_system.model.CountRequest;
import com.ouction.ouction_system.service.AuctionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class OuctionSystemApplication {

	public static void main(String[] args) {

		ApplicationContext context=SpringApplication.run(OuctionSystemApplication.class, args);


		AuctionService auctionService=context.getBean(AuctionService.class);

		List<CountRequest> res= auctionService.getSellerBidCounts();
		System.out.println(res);


	}

}
