package com.monash.mymonashmate.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.monash.mymonashmate.entity.UserAccess;

public class UserAccessClient extends AbstractClient{
	
	public UserAccess getUserById(String id) {
		// The URL for making the GET request
		final String url = BASE_URL + "/useraccess/{id}";
		// Create RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		return restTemplate.getForObject(url, UserAccess.class, id);
	}
	
	
	public UserAccess getUserByName(String name) {
		// The URL for making the GET request
		final String url = BASE_URL + "/useraccess/username/{name}";
		// Create RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());//makesure all request use jason style

		return restTemplate.getForObject(url, UserAccess.class, name);
	}
	
	public UserAccess createNewUser(UserAccess user) {

		String url = BASE_URL + "/useraccess/add";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);

		// create the request entity
		HttpEntity<UserAccess> requestEntity = new HttpEntity<UserAccess>(user,
				requestHeaders);
		// Get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		try {
			ResponseEntity<UserAccess> response = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, UserAccess.class);
			System.out.println("response.getBody():" + response.getBody());
			HttpStatus status = response.getStatusCode();
			System.out.println(status);
			Log.i("HttpStatus: ", status.name());
			if (status == HttpStatus.OK) {
				return response.getBody();
			} else {
				return null;
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return null;
		}

	}
	

}
