package com.mcservice.movieinfoservice.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.mcservice.movieinfoservice.models.Movie;
import com.mcservice.movieinfoservice.models.MovieSummary;

@RestController
@RequestMapping(value="/movies")
public class MovieResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieResource.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${api.key}")
	private String apiKey;
	
	@GetMapping(value="/{movieId}")
	public Movie geMovieInfor(@PathVariable("movieId")String movieId){
		LOGGER.info("IN geMovieInfor");
		String url = "https://api.themoviedb.org:443/3/movie/" + movieId +"?api_key=" + apiKey;
		
		HttpHeaders header = new HttpHeaders();
		header.set("User-Agent", "Mozilla");
		HttpEntity<String> requestEntity = new HttpEntity<>(header);
		MovieSummary movieSummary = getLiveData(url, false);
		//ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
		//ResponseEntity<MovieSummary> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, MovieSummary.class);
		LOGGER.info("OUT geMovieInfor");
		if(movieSummary != null){
			return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());						
		}else{
			return new Movie(movieId, "dummy name", "dummy description");
		}
	}
	
	private MovieSummary getLiveData(String url, boolean tryOnce){
		MovieSummary movieSummary = null;
		try{
			movieSummary = restTemplate.getForObject(url, MovieSummary.class);
			LOGGER.info("\n\n\n===================SUCCESS=========================\n\n\n");
		}catch(ResourceAccessException resourceAccessException){
			LOGGER.info("ResourceAccessException occurred");
			if(tryOnce){
				return getLiveData(url, false);
			}
		}
		
		return movieSummary;
	}
}


