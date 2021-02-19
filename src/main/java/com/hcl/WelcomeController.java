package com.hcl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WelcomeController {

	@GetMapping("/getContactDetails")
	public String home(@RequestParam("code") String code) throws URISyntaxException {
		    
		RestTemplate restTemplate = new  RestTemplate();
	
		
		
		final String baseUrl = "https://oauth2.googleapis.com/token";
	    URI uri = new URI(baseUrl);
	    GoogleRequest apiRequest = new GoogleRequest();
	    apiRequest.setClient_id("332446793757-qvagqm30mlgfr0mk1f6se2f9q93tngip.apps.googleusercontent.com");
	    apiRequest.setClient_secret("qLwlk7Vh1c4vpCXzaD-u7444");
	    apiRequest.setCode(code);
	    apiRequest.setGrant_type("authorization_code");
	    apiRequest.setRedirect_uri("http://localhost:9001/getContactDetails");
	     
	    HttpHeaders headers = new HttpHeaders();   
	   
	    
	 
	    HttpEntity<GoogleRequest> request = new HttpEntity<>(apiRequest, headers);
	     
	      ResponseEntity<HashMap> postForEntity = restTemplate.postForEntity(uri, request, HashMap.class);
	      System.out.println(postForEntity.toString());
	      HashMap<String, String> body = postForEntity.getBody();
	      Object object = body.get("access_token");
	      System.out.println(object.toString());
	     
	 
	 HttpHeaders getHeaders = new HttpHeaders();
	 getHeaders.setBearerAuth(object.toString());
	HttpEntity<Object> entityContacts = new HttpEntity<>(getHeaders);
	
	
	 ResponseEntity<String> exchange = restTemplate.exchange("https://people.googleapis.com/v1/people/me/connections?personFields=names,emailAddresses", HttpMethod.GET, entityContacts, String.class);
	System.out.println(exchange.toString());	
	 
	 return exchange.toString();
	}

	

}
