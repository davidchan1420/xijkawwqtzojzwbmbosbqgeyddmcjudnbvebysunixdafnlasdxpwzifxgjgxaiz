package movieservice.service;

import java.util.List;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;

public interface SearchService {

	
//	public List<Movie> searchMovies(SearchCriteria searchCriteria);
	
	public List<Movie> filterMovies(SearchCriteria searchCriteria, final List<Movie> movies);
	
	public List<Movie> filterByMovieName(SearchCriteria searchCriteria, final List<Movie> movies);
	
	public List<Movie> filterByCinema(SearchCriteria searchCriteria, final List<Movie> movies);
	
	public List<Movie> filterByDistance(SearchCriteria searchCriteria, final List<Movie> movies);
	
	public List<Movie> filterByTime(SearchCriteria searchCriteria, final List<Movie> movies);
	
	public void sortMovie(SearchCriteria searchCriteria, final List<Movie> movies);
}
