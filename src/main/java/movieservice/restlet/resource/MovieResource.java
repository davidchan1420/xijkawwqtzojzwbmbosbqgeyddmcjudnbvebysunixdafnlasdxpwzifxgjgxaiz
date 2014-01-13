package movieservice.restlet.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

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
	
//	@GET
//	@Path("getMovies/{searchCriteria}")
//	public String getMovies(@PathParam("searchCriteria") String argSearchCriteria){
//		
//		Gson gson = new Gson();
//		SearchCriteria searchCriteria = gson.fromJson(argSearchCriteria, SearchCriteria.class);
//		
//		SearchServiceImpl searchService = new SearchServiceImpl();
//		List<Movie> result = searchService.filterMovies(searchCriteria, searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? moviesChi : moviesEng);		
//		
//		for (int i = 0; i < result.size(); i++) {
//			Movie movie = result.get(i);
//			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
//		}
//		System.out.println("result size: " + result.size());
//		
//		String gsonResult = gson.toJson(result);
//		return gsonResult;
//	}
	
	@GET
	@Path("getMovies/{distance}")
	public String getMovies(@PathParam("distance") String distance){
		
		return getMovies(distance, null, null);		
	}
	
	@GET
	@Path("getMovies/{distance}/{movieName}")
	public String getMovies(@PathParam("distance") String distance, @PathParam("movieName") String movieName){
		
		return getMovies(distance, movieName, null);		
	}
	
	@GET
	@Path("getMovies/{distance}/{movieName}/{cinema}")
	public String getMovies(@PathParam("distance") String distance, @PathParam("movieName") String movieName, @PathParam("cinema") String cinema){
		
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setLanguage("CHI");
//		searchCriteria.setLanguage("ENG");		
		searchCriteria.setX(22.3291015D);
		searchCriteria.setY(114.1882631D);
		
		List<SearchCriteria.ShowingDate> listShowingDate = new ArrayList<SearchCriteria.ShowingDate>();		
		
		Calendar cal = CalendarUtil.getSystemCalendar();		
		
		SearchCriteria.ShowingDate showingDate = searchCriteria.new ShowingDate();		
		showingDate.setShowingDate(cal);		
		listShowingDate.add(showingDate);
		
		showingDate = searchCriteria.new ShowingDate();
		cal.add(Calendar.DATE, 1);		
		showingDate.setShowingDate(cal);		
		listShowingDate.add(showingDate);
		
		showingDate = searchCriteria.new ShowingDate();
		cal.add(Calendar.DATE, 1);		
		showingDate.setShowingDate(cal);		
		listShowingDate.add(showingDate);
		
		searchCriteria.setShowingDates(listShowingDate);
		
		searchCriteria.setDistanceRange(Integer.parseInt(distance));
		searchCriteria.setMovieName(movieName);
		searchCriteria.setCinema(cinema);
		
		SearchServiceImpl searchService = new SearchServiceImpl();
		List<Movie> result = searchService.filterMovies(searchCriteria, searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? moviesChi : moviesEng);		
		
		for (int i = 0; i < result.size(); i++) {
			Movie movie = result.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
		}
		System.out.println("result size: " + result.size());
		
		Gson gson = new Gson();
		String gsonResult = gson.toJson(result);		
//		return gsonResult;
		return null;
	}
	
//	public static void main(String[] args){
//		
//		SearchCriteria searchCriteria = new SearchCriteria();
//		
//		List<SearchCriteria.ShowingDate> list = new ArrayList<SearchCriteria.ShowingDate>();		
//		
//		Calendar cal = CalendarUtil.getSystemCalendar();		
//		SearchCriteria.ShowingDate showingDate = searchCriteria.new ShowingDate();
//		
//		showingDate.setShowingDate(cal);		
//		list.add(showingDate);
//						
//		showingDate = searchCriteria.new ShowingDate();
//		cal.add(Calendar.DATE, 10);
//		
//		showingDate.setShowingDate(cal);		
//		list.add(showingDate);
//		
//		for(int i=0; i < list.size(); i++){
//			
//			SearchCriteria.ShowingDate ssd = list.get(i);
//			Calendar sdMin = ssd.getShowingDateMin();
//			Calendar sdMax = ssd.getShowingDateMax();
//			System.out.println(i + ": " + "Min: " + sdMin.getTime() + ", Max: " + sdMax.getTime());
//		}
//	}

}


