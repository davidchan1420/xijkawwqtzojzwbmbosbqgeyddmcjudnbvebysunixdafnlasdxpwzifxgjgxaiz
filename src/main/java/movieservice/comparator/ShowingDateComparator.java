package movieservice.comparator;

import java.util.Comparator;

import movieservice.domain.Movie;

public class ShowingDateComparator implements Comparator<Movie> {

	
	@Override
	public int compare(Movie o1, Movie o2) {
	
		return o1.getShowingDate().compareTo(o2.getShowingDate());
	}

}
