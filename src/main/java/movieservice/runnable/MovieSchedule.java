package movieservice.runnable;

import java.util.List;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.restlet.resource.MovieResource;
import movieservice.service.impl.MovieServiceImpl;
import movieservice.util.CalendarUtil;
import movieservice.util.ConstantUtil;

public class MovieSchedule implements Runnable {

	private int flag = 0;
	private String language = null;
	
	public MovieSchedule(String language) {
		this.language = language;
	}

//	@Override
//	public void run() {
//		try {
//			flag++;
//			if (flag % 5 == 0) {
//				System.out.println("Exception...");
//				throw new RuntimeException();
//			} else {
//				System.out.println(flag + ": " + CalendarUtil.getSystemDate());
//			}
//		} catch (Exception e) {
//
//		}
//	}
	
	@Override
	public void run() {

		try{
			flag++;
			
			if (flag % 5 == 0) {
				System.out.println("Exception...");
				throw new RuntimeException();
			}			
		
			System.out.println(flag + ", start at: " + CalendarUtil.getSystemDate());
			MovieServiceImpl movieServiceImpl = new MovieServiceImpl();			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setLanguage(this.language);
			
			List<Movie> movies = movieServiceImpl.getAllMovies(searchCriteria);
			
			if(this.language.equalsIgnoreCase(ConstantUtil.LANG_CHI)){
				MovieResource.setMoviesChi(movies);
				System.out.println("CHI Size is: " + MovieResource.getMoviesChi().size());
			}
			if(this.language.equalsIgnoreCase(ConstantUtil.LANG_ENG)){
				MovieResource.setMoviesEng(movies);
				System.out.println("ENG Size is: " + MovieResource.getMoviesEng().size());
			}			
			
			System.out.println(flag + ", end at: " + CalendarUtil.getSystemDate());
			
			
		} catch (Exception e){
			System.out.println("Movie Extraction Exception occurrs...");
		}

	}

}
