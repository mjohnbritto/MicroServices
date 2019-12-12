package com.mcservice.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.mcservice.moviecatalogservice.models.CatalogItem;
import com.mcservice.moviecatalogservice.models.Movie;
import com.mcservice.moviecatalogservice.models.Rating;
import com.mcservice.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;

	@GetMapping(value = "/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// get all rated movie ids
		UserRating userRating = userRatingInfo.getUserRating(userId);
		List<Rating> ratings = userRating.getUserRating();

		return ratings.stream().map(rating -> movieInfo.getCatalogItem(rating)).collect(Collectors.toList());

		/*
		 * Movie movie = webClientBuilder.build() .get()
		 * .uri("http://localhost:8082/movies/" + rating.getMovieId())
		 * .retrieve() .bodyToMono(Movie.class) .block();
		 */
	}
}