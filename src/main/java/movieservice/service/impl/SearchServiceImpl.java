package movieservice.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import movieservice.comparator.CinemaComparator;
import movieservice.comparator.DistanceComparator;
import movieservice.comparator.MovieNameComparator;
import movieservice.comparator.ShowingDateComparator;
import movieservice.comparator.MovieComparator;
import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.SearchService;
import movieservice.util.MovieUtil;

public class SearchServiceImpl implements SearchService {

//	@Override
//	public List<Movie> searchMovies(SearchCriteria searchCriteria) {
//				
//		MovieServiceImpl searchService = new MovieServiceImpl();
//		List<Movie> movies = searchService.getAllMovies(searchCriteria);
//		
//		List<Movie> filteredMovies = filterMovies(searchCriteria, movies);
//		return filteredMovies;
//	}

	@Override
	public List<Movie> filterMovies(SearchCriteria searchCriteria, List<Movie> movies) {
		
		List<Movie> result = new ArrayList<Movie>(movies);

		List<Movie> times = filterByTime(searchCriteria, result);
		result.retainAll(times);
		
		List<Movie> movieNames = filterByMovieName(searchCriteria, result);
		result.retainAll(movieNames);
		
		List<Movie> cinemas = filterByCinema(searchCriteria, result);
		result.retainAll(cinemas);
		
		List<Movie> distances = filterByDistance(searchCriteria, result);
		result.retainAll(distances);
		
		sortMovie(searchCriteria, result);
		
		return result;
	}
	
	
	public void sortMovie(SearchCriteria searchCriteria, final List<Movie> movies){
		
		Collections.sort(movies, new MovieComparator(searchCriteria));
		
	}
	

	@Override
	public List<Movie> filterByMovieName(SearchCriteria searchCriteria, final List<Movie> movies) {		
					
		if(searchCriteria.getMovieName() == null)
			return movies;
		
		String searchMovieName = searchCriteria.getMovieName().trim().toLowerCase();
		if(searchMovieName.length() < 1)
			return movies;		
		
		List<Movie> result = new ArrayList<Movie>();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);			
			//if(movie.getMovieName().equalsIgnoreCase(movieName)){
			String movieName = movie.getMovieName().toLowerCase();
			if(movieName.contains(searchMovieName)){
				result.add(movie);				
			}			
		}		
		return result;
	}

	@Override
	public List<Movie> filterByCinema(SearchCriteria searchCriteria, List<Movie> movies) {
		
		if(searchCriteria.getCinema() == null)
			return movies;
		
		String searchCinema = searchCriteria.getCinema().trim().toLowerCase();
		if(searchCinema.length() < 1)
			return movies;		
		
		List<Movie> result = new ArrayList<Movie>();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			
			String cinema = movie.getCinema().toLowerCase();
			
			if(cinema.contains(searchCinema)){
				result.add(movie);				
			}			
		}		
		return result;
	}
	
	@Override
	public List<Movie> filterByDistance(SearchCriteria searchCriteria, List<Movie> movies) {
		
//		if(searchCriteria.getDistanceRange() == null)
//			return movies;
		if(searchCriteria.getX() == null || searchCriteria.getY() == null)
			return movies;
		
		List<Movie> result = new ArrayList<Movie>();
		
		Double searchDistance = searchCriteria.getDistanceRange().doubleValue();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			Double relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, movie.getCoordinate());
			
//			if(relativeDistance <= searchDistance){
			if(searchDistance == 0 || relativeDistance <= searchDistance){
				movie.setRelativeDistance(relativeDistance);
				result.add(movie);
			}			
		}
		
		return result;
	}

	@Override
	public List<Movie> filterByTime(SearchCriteria searchCriteria, List<Movie> movies) {
		
		List<Movie> result = new ArrayList<Movie>();
		
		List<SearchCriteria.ShowingDate> searchShowingDates = searchCriteria.getShowingDates();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			Calendar movieShowingDate = movie.getShowingDate();
			
			for(int h=0; h < searchShowingDates.size(); h++){
				
				SearchCriteria.ShowingDate searchShowingDate = searchShowingDates.get(h);
				Calendar searchShowingDateMax = searchShowingDate.getShowingDateMax();		
				Calendar searchShowingDateMin = searchShowingDate.getShowingDateMin();
				
				if(searchShowingDateMax.after(movieShowingDate) && (searchShowingDateMin.before(movieShowingDate) || searchShowingDateMin.equals(movieShowingDate))){
					result.add(movie);
				}
			}			
		}
		
		return result;
	}

//	public static void main(String[] args) {
//
//		SearchCriteria searchCriteria = new SearchCriteria();
//		searchCriteria.setLanguage("CHI");
////		searchCriteria.setLanguage("ENG");		
//		searchCriteria.setX(22.3291015D);
//		searchCriteria.setY(114.1882631D);
//		
//		Calendar searchDate = CalendarUtil.getSystemCalendar();		
//		searchDate.add(Calendar.DATE, 1);
//		searchCriteria.setShowingDate(searchDate);
//		
//		searchCriteria.setDistanceRange(7);
//		searchCriteria.setMovieName("哈比人");
//		
//		SearchServiceImpl searchService = new SearchServiceImpl();
//		List<Movie> list = searchService.searchMovies(searchCriteria);
//		
//		
//		for (int i = 0; i < list.size(); i++) {
//			Movie movie = list.get(i);
//			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
//		}
//		System.out.println("list size: " + list.size());
//		
//	}


	
}
