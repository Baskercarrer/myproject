package com.mycompany.core.APIDriver;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIDriver {

	private String endPointURL;
	private Response currentResponse;
	
	public APIDriver(String endPointURL){
		this.endPointURL = endPointURL;
	}
	public void setEndPoint(String endPoint){
		this.endPointURL = endPoint;
	}
	
	public String getEndPointURL(){
		return endPointURL;
	}
	
	public Response get(String resourcePath){
		return RestAssured.get(getEndPointURL() + resourcePath);	
	}
	
	public Response Post(String resourcePath){
		return RestAssured.post(getEndPointURL() + resourcePath);
	}
	
	public Response Put(String resourcePath){
		return RestAssured.put(getEndPointURL() + resourcePath);
	
	}
	
	public Response Delete(String resourcePath){
		return RestAssured.delete(getEndPointURL() + resourcePath);
	}
	
	public Response Patch(String resourcePath){
		return RestAssured.patch(getEndPointURL() + resourcePath);
	}

}
