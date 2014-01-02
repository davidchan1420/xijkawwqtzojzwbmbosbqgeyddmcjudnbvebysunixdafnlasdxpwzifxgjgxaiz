package movieservice.restlet.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.impl.SearchServiceImpl;
import movieservice.util.CalendarUtil;
import movieservice.util.ConstantUtil;

@Path("movie")
public class MovieResource {	
	
	private static List<Movie> moviesChi = new ArrayList<Movie>();
	private static List<Movie> moviesEng = new ArrayList<Movie>();
	
	public static List<Movie> getMoviesChi() {
		return moviesChi;
	}

	public static List<Movie> getMoviesEng() {
		return moviesEng;
	}
	
	public static void setMoviesChi(List<Movie> moviesChi) {
		MovieResource.moviesChi = moviesChi;
	}

	public static void setMoviesEng(List<Movie> moviesEng) {
		MovieResource.moviesEng = moviesEng;
	}
	
	@GET
	@Path("getMovies/{searchCriteria}")
	public String getMovies(@PathParam("searchCriteria") String arg1){
		
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setLanguage("CHI");
//		searchCriteria.setLanguage("ENG");		
		searchCriteria.setX(22.3291015D);
		searchCriteria.setY(114.1882631D);
		
		Calendar searchDate = CalendarUtil.getSystemCalendar();		
		searchDate.add(Calendar.DATE, 1);
		searchCriteria.setShowingDate(searchDate);
		
//		searchCriteria.setDistanceRange(7);
//		searchCriteria.setMovieName("哈比人");
		
		SearchServiceImpl searchService = new SearchServiceImpl();
		List<Movie> result = searchService.filterMovies(searchCriteria, searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? moviesChi : moviesEng);		
		
		for (int i = 0; i < result.size(); i++) {
			Movie movie = result.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
		}
		System.out.println("result size: " + result.size());
		
		return null;		
	}


}


