//package junit;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import movieservice.comparator.CinemaComparator;
//import movieservice.domain.Movie;
//
//import org.junit.Test;
//
//
//public class TestSorting {
//
//	@Test
//	public void test() {
//		
//		
//		List<Movie> list = new ArrayList<Movie>();
//		
//		
//		Movie movie = new Movie();
//		movie.setCinema("C");		
//		list.add(movie);
//		
//		movie = new Movie();
//		movie.setCinema("B");
//		list.add(movie);
//		
//		movie = new Movie();
//		movie.setCinema("A");
//		list.add(movie);
//		
//		for(int i=0; i<list.size(); i++){
//			
//			Movie temp = list.get(i);			
//			System.out.println(temp.getCinema());
//
//		}
//		
//		System.out.println("----------");
//		Collections.sort(list, new CinemaComparator());
//
//		for(int i=0; i<list.size(); i++){
//			
//			Movie temp = list.get(i);			
//			System.out.println(temp.getCinema());
//
//		}
//		
//	}
//
//}
