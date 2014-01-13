package movieservice.comparator;

import java.util.Comparator;

import movieservice.domain.Movie;

public class CinemaComparator implements Comparator<Movie> {

	
	@Override
	public int compare(Movie o1, Movie o2) {
		
		return o1.getCinema().compareTo(o2.getCinema());
		
	}
	
	

}
