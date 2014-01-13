package movieservice.domain;

import java.util.Calendar;
import java.util.List;

import movieservice.util.CalendarUtil;

public class SearchCriteria {

	private String language;
	private String movieName;
	private String cinema;

	public class ShowingDate{
		private Calendar showingDateMin;
		private Calendar showingDateMax;
		public Calendar getShowingDateMin() {
			return showingDateMin;
		}
		public Calendar getShowingDateMax() {
			return showingDateMax;
		}
		
		public void setShowingDate(Calendar showingDate){
		
			Calendar showingDateMax = CalendarUtil.trimDayToMax(showingDate.getTime());
			this.showingDateMax = showingDateMax;
			
			Calendar showingDateMin = CalendarUtil.trimDayToMin(showingDate.getTime());
			this.showingDateMin = showingDateMin;		
		}
		
	}
	
	private List<ShowingDate> showingDates;

	private Double x;
	private Double y;
	private Integer distanceRange;
		

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}	
	
	public String getCinema() {
		return cinema;
	}

	public void setCinema(String cinema) {
		this.cinema = cinema;
	}

	public List<ShowingDate> getShowingDates() {
		return showingDates;
	}

	public void setShowingDates(List<ShowingDate> showingDates) {
		this.showingDates = showingDates;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Integer getDistanceRange() {
		return distanceRange;
	}

	public void setDistanceRange(Integer distanceRange) {
		this.distanceRange = distanceRange;
	}

	

}
