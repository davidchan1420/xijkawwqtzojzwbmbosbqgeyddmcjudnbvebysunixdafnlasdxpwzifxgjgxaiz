package movieservice.domain;

import java.util.Calendar;

import movieservice.util.CalendarUtil;

public class SearchCriteria {

	private String language;
	private String movieName;
	private Calendar showingDateMin;
	private Calendar showingDateMax;
	
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

	public void setShowingDate(Calendar showingDate){
		
		Calendar showingDateMax = CalendarUtil.trimDayToMax(showingDate.getTime());
		this.showingDateMax = showingDateMax;
		
		Calendar showingDateMin = CalendarUtil.trimDayToMin(showingDate.getTime());
		this.showingDateMin = showingDateMin;		
	}
	
	public Calendar getShowingDateMin() {
		return showingDateMin;
	}

//	public void setShowingDateMin(Calendar showingDate) {
//		this.showingDateMin = showingDate;
//	}	

	public Calendar getShowingDateMax() {
		return showingDateMax;
	}

//	public void setShowingDateMax(Calendar showingDateMax) {
//		this.showingDateMax = showingDateMax;
//	}

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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
