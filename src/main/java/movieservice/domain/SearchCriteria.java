package movieservice.domain;

import java.util.Calendar;

public class SearchCriteria {

	private String language;
	private String movieName;
	private Calendar showingDate;
	
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

	public Calendar getShowingDate() {
		return showingDate;
	}

	public void setShowingDate(Calendar showingDate) {
		this.showingDate = showingDate;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
