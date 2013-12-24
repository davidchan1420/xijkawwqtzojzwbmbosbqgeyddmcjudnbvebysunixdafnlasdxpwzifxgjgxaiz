package movieservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.SearchService;

public class SearchServiceImpl implements SearchService {

	@Override
	public List<Movie> searchMovies(SearchCriteria searchCriteria) {
				
		MovieServiceImpl searchService = new MovieServiceImpl();
		List<Movie> movies = searchService.getAllMovies(searchCriteria);
		
		List<Movie> filteredMovies = filterMovies(searchCriteria, movies);
		return filteredMovies;		
	}

	@Override
	public List<Movie> filterMovies(SearchCriteria searchCriteria, List<Movie> movies) {
		
		List<Movie> result = new ArrayList<Movie>();
		
		if(searchCriteria.getMovieName() != null && searchCriteria.getMovieName().trim().length() > 0){
			result = filterByName(searchCriteria, movies);
		}
		
		return result;
	}

	@Override
	public List<Movie> filterByName(SearchCriteria searchCriteria, final List<Movie> movies) {
		
		String movieName = searchCriteria.getMovieName();
		
		List<Movie> result = new ArrayList<Movie>();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			//if(movie.getMovieName().equalsIgnoreCase(movieName)){
			if(movie.getMovieName().toLowerCase().contains(movieName.toLowerCase())){
				result.add(movie);				
			}			
		}
		
		return result;
	}

	@Override
	public List<Movie> filterByDistance(SearchCriteria searchCriteria, List<Movie> movies) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movie> filterByTime(SearchCriteria searchCriteria, List<Movie> movies) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setLanguage("CHI");
//		searchCriteria.setLanguage("ENG");
		searchCriteria.setDistanceRange(5000);
		searchCriteria.setX(22.3291015D);
		searchCriteria.setY(114.1882631D);	
		searchCriteria.setMovieName("SuG");
		
		SearchServiceImpl searchService = new SearchServiceImpl();
		List<Movie> list = searchService.searchMovies(searchCriteria);
		
		
		for (int i = 0; i < list.size(); i++) {
			Movie movie = list.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate() + ", Fee: $" + movie.getFee());
		}

		System.out.println("list size: " + list.size());		
		
	}

	
}
