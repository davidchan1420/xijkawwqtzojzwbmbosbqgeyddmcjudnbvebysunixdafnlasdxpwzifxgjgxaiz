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


@Path("movie")
public class MovieResource {	
	
	private static List<Movie> movies = new ArrayList<Movie>();
	
	public static List<Movie> getMovies() {
		return movies;
	}

	public static void setMovies(List<Movie> movies) {
		MovieResource.movies = movies;
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
		List<Movie> list = searchService.filterMovies(searchCriteria, movies);		
		
		for (int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
		}
		System.out.println("list size: " + list.size());
		
		return null;		
	}


}


