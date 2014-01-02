package movieservice.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.SearchService;
import movieservice.util.CalendarUtil;

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
		
		List<Movie> names = filterByName(searchCriteria, result);
		result.retainAll(names);
		
		List<Movie> distances = filterByDistance(searchCriteria, result);
		result.retainAll(distances);			
		
		return result;
	}

	@Override
	public List<Movie> filterByName(SearchCriteria searchCriteria, final List<Movie> movies) {		
					
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
	public List<Movie> filterByDistance(SearchCriteria searchCriteria, List<Movie> movies) {
		
		if(searchCriteria.getDistanceRange() == null)
			return movies;
		
		List<Movie> result = new ArrayList<Movie>();
		
		Double searchDistance = searchCriteria.getDistanceRange().doubleValue();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			//TODO: TO BE CHANGED
			Double relativeDistance = movie.getRelativeDistance();
			if(relativeDistance <= searchDistance){
				result.add(movie);
			}			
		}
		
		return result;
	}

	@Override
	public List<Movie> filterByTime(SearchCriteria searchCriteria, List<Movie> movies) {
		
		List<Movie> result = new ArrayList<Movie>();
		
		Calendar searchDateMax = searchCriteria.getShowingDateMax();		
		Calendar searchDateMin = searchCriteria.getShowingDateMin();
		
		for(int i=0; i<movies.size(); i++){
			Movie movie = movies.get(i);
			Calendar showingDate = movie.getShowingDate();
			if(searchDateMax.after(showingDate) && searchDateMin.before(showingDate)){
				result.add(movie);
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
