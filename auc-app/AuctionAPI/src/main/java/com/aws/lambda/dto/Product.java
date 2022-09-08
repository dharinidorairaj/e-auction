package com.aws.lambda.dto;

import java.util.Date;

public class Product {
	public Long id;
	public String productName;
	public String shortDesc;
	public String detailedDesc;
	public String category;
	public int startingPrice;
	public String bidEndDate;
	
	public Product() {
		
	}
	public Product(Long id,String productName, String shortDesc, String detailedDesc, 
			String category, int startingPrice, String bidEndDate) {
		this.id = id;
		this.productName = productName;
		this.shortDesc = shortDesc;
		this.detailedDesc = detailedDesc;
		this.category = category;
		this.startingPrice = startingPrice;
		this.bidEndDate = bidEndDate;
	}
}
