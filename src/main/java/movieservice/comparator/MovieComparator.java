package movieservice.comparator;

import java.util.Comparator;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;

public class MovieComparator implements Comparator<Movie> {

	private SearchCriteria searchCriteria;

	public MovieComparator(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	@Override
	public int compare(Movie o1, Movie o2) {

		// ***** Always sort first if user chose to sort by distance *****
		if (searchCriteria.getDistanceRange() != null) {

			int distance = compareDistance(o1, o2);
			if (distance != 0) {
				return distance;
			}
		}

//		if (searchCriteria.getMovieName() != null) {

			int name = compareMovieName(o1, o2);
			if (name != 0) {
				return name;
			}
//		}

//		if (searchCriteria.getCinema() != null) {

			int cinema = compareCinema(o1, o2);
			if (cinema != 0) {
				return cinema;
			}
//		}

//		if (searchCriteria.getShowingDates() != null){
			
			int showingDate = compareShowingDate(o1, o2);
//			if (showingDate != 0){
			return showingDate;
//			}
			
//		}	

	}

	private int compareDistance(Movie o1, Movie o2) {

		DistanceComparator distanceComp = new DistanceComparator();
		return distanceComp.compare(o1, o2);
	}

	private int compareMovieName(Movie o1, Movie o2) {

		MovieNameComparator nameComp = new MovieNameComparator();
		return nameComp.compare(o1, o2);
	}

	private int compareCinema(Movie o1, Movie o2) {

		CinemaComparator cinemaComp = new CinemaComparator();
		return cinemaComp.compare(o1, o2);
	}

	private int compareShowingDate(Movie o1, Movie o2) {

		ShowingDateComparator showingDateComp = new ShowingDateComparator();
		return showingDateComp.compare(o1, o2);
	}

}
