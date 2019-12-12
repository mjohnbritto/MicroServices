package com.mcservice.ratingsdataservice.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mcservice.ratingsdataservice.models.Rating;
import com.mcservice.ratingsdataservice.models.UserRating;

@RestController
@RequestMapping(value="/ratingsdata")
public class RatingDataResource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RatingDataResource.class);

	@GetMapping(value="/{movieId}")
	public Rating getRating(@PathVariable("movieId")String movieId){
		return new Rating(movieId, 5);
	}
	
	@GetMapping(value="/users/{userId}")
	public UserRating getRatings(@PathVariable("userId")String userId){
		LOGGER.info("IN getRatings");
		List<Rating> ratings=Arrays.asList(new Rating("11", 6),new Rating("22", 8));
		LOGGER.info("OUT getRatings");
		return new UserRating(ratings);
	}
}
