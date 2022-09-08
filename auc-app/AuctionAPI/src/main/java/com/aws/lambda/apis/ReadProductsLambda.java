package com.aws.lambda.apis;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.dto.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadProductsLambda {
	
	public APIGatewayProxyResponseEvent getProducts(APIGatewayProxyRequestEvent request) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
//		Product product = new Product(123l, "product1", "product1 product1 test", "product1 product1 test detailed desc", "product category test", 100, new Date());
		AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("PRODUCTS_TABLE")));
		List<Product> products = scanResult.getItems().stream()
		.map(item->new Product(Long.parseLong(item.get("id").getN()), item.get("itemId").getS(),
				item.get("shortDesc").getS(), item.get("detailedDesc").getS(), item.get("category").getS(),
				Integer.parseInt(item.get("startingPrice").getN()),item.get("bidEndDate").getS())).collect(Collectors.toList());
				
		String jsonOutput = objectMapper.writeValueAsString(products);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
	}

}
