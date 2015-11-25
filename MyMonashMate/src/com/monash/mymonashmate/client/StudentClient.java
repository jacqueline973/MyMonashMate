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

import com.monash.mymonashmate.entity.MatchCriteria;
import com.monash.mymonashmate.entity.MatchMateResult;
import com.monash.mymonashmate.entity.Profile;
import com.monash.mymonashmate.entity.Student;

public class StudentClient extends AbstractClient {
	
	public Student getUserById(String id) {
		// The URL for making the GET request
		final String url = BASE_URL + "/student/{id}";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());

		return restTemplate.getForObject(url, Student.class, id);
	}
	
	public boolean createNewProfile(Profile profile) {
		String url = BASE_URL + "/student/profile";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		// create the request entity
		HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(profile,
				requestHeaders);
		// Get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		try {
			ResponseEntity<Profile> response = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, Profile.class);
			System.out.println(response.getBody());
			HttpStatus status = response.getStatusCode();
			System.out.println(status);
			Log.i("HttpStatus: ", status.name());
			if (status == HttpStatus.CREATED || status == HttpStatus.NO_CONTENT) {
				return true;
			} else {
				return false;
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateProfile(Profile profile) {
		String url = BASE_URL + "/student/profile/update";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		// create the request entity
		HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(profile,
				requestHeaders);
		// Get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		try {
			ResponseEntity<Profile> response = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, Profile.class);
			System.out.println(response.getBody());
			HttpStatus status = response.getStatusCode();
			System.out.println(status);
			Log.i("HttpStatus: ", status.name());
			if (status == HttpStatus.CREATED || status == HttpStatus.NO_CONTENT) {
				return true;
			} else {
				return false;
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateStudentSetting(Profile profile) {
		String url = BASE_URL + "/student/profile/studentsetting";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		// create the request entity
		HttpEntity<Profile> requestEntity = new HttpEntity<Profile>(profile,
				requestHeaders);
		// Get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		try {
			ResponseEntity<Profile> response = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, Profile.class);
			System.out.println(response.getBody());
			HttpStatus status = response.getStatusCode();
			System.out.println(status);
			Log.i("HttpStatus: ", status.name());
			if (status == HttpStatus.CREATED || status == HttpStatus.NO_CONTENT) {
				return true;
			} else {
				return false;
			}
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Profile getProfile(String studentId) {
		String url = BASE_URL + "/student/profile/{studentId}";
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJacksonHttpMessageConverter());
		return restTemplate.getForObject(url, Profile.class, studentId);
	}
	
	public MatchMateResult searchMonashMate(MatchCriteria criteria) {
		String url = BASE_URL + "/student/match";
		HttpHeaders requestHeaders = new HttpHeaders();
		// Set the Content-Type header
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		// create the request entity
		HttpEntity<MatchCriteria> requestEntity = new HttpEntity<MatchCriteria>(criteria,
				requestHeaders);
		// Get the RestTemplate and add the message converters
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
		try {
			ResponseEntity<MatchMateResult> response = restTemplate.exchange(url,
					HttpMethod.POST, requestEntity, MatchMateResult.class);
			System.out.println("Update setting: " + response.getBody());
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
