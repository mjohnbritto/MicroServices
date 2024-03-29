package com.mcservice.moviecatalogservice.resource;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mcservice.moviecatalogservice.models.Rating;
import com.mcservice.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfo {
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackUserRating") 
	public UserRating getUserRating(String userId) {
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId,
				UserRating.class);
		return userRating;
	}
	
	public UserRating getFallbackUserRating(String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRating(Arrays.asList(new Rating("-1",-1)));
		return userRating;
	}
}
