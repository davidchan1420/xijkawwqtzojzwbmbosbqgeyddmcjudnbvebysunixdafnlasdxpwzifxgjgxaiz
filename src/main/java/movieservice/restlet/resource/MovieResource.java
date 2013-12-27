package movieservice.restlet.resource;

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
		
		searchCriteria.setDistanceRange(7);
		searchCriteria.setMovieName("哈比人");
		
		SearchServiceImpl searchService = new SearchServiceImpl();
		List<Movie> list = searchService.searchMovies(searchCriteria);
		
		
		for (int i = 0; i < list.size(); i++) {
			Movie movie = list.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
		}
		System.out.println("list size: " + list.size());
		
		return null;
		
	}



}


