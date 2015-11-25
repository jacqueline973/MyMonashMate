package com.monash.mymonashmate.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.monash.mymonashmate.entity.Course;
import com.monash.mymonashmate.entity.Unit;

public class CourseClient extends AbstractClient {

	public List<Course> getAllCourses() {
		// The URL for making the GET request
		final String url = BASE_URL + "/course/all";
		// This is where we get the RestTemplate and add the message converters
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	  //This is where we call the exchange method and process the response    
//	    ResponseEntity<CourseContainer> responseEntity =  restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(), CourseContainer.class);
//	    return responseEntity.getBody().getCourses();
	    ResponseEntity<Course[]> responseEntity = restTemplate.getForEntity(url, Course[].class);
	    return Arrays.asList(responseEntity.getBody());
	}
	
	
	public List<Unit> getUnitsByCourses(String courses) {
		
		final String url = BASE_URL + "/course/unit/{courseCodeList}";
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	    
	    ResponseEntity<Unit[]> responseEntity = restTemplate.getForEntity(url, Unit[].class, courses);
	    return Arrays.asList(responseEntity.getBody());
	}
	

}
