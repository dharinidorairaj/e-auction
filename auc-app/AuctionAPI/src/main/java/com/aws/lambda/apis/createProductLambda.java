package com.aws.lambda.apis;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.dynamodbv2.document.Table;

public class createProductLambda {

	public APIGatewayProxyResponseEvent createProduct(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(request.getBody(), Product.class);
		DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
		Table table = dynamoDB.getTable(System.getenv("PRODUCTS_TABLE"));
		Item item = new Item().withPrimaryKey("id", product.id).withString("itemId", product.productName)
				.withString("shortDesc", product.shortDesc).withString("detailedDesc", product.detailedDesc)
				.withString("category", product.category).withInt("startingPrice", product.startingPrice)
				.withString("bidEndDate", product.bidEndDate);		
		table.putItem(item);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("Product ID:" + product.id);
	}
}
