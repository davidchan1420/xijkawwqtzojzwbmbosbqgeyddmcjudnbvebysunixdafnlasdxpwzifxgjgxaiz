package movieservice.service;

import java.util.List;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;

public interface MovieService {

	public List<Movie> getMCLMovies(SearchCriteria searchCriteria);
	
	public List<Movie> getTheGrandMovies(SearchCriteria searchCriteria);

	public List<Movie> getUAMovies(SearchCriteria searchCriteria);

	public List<Movie> getGoldenHarvestMovies(SearchCriteria searchCriteria);

	public List<Movie> getBroadwayMovies(SearchCriteria searchCriteria);
	
	public List<Movie> getAMCMovies(SearchCriteria searchCriteria);

	public List<Movie> getAllMovies(SearchCriteria searchCriteria);
	

	
}
