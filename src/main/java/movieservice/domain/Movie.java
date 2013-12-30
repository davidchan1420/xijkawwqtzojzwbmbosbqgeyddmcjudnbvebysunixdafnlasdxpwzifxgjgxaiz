package movieservice.domain;

import java.util.Calendar;

import movieservice.util.Coordinate;

public class Movie {
	
	private String movieName;
	private String cinema;
	private Calendar showingDate;
	private Integer fee;

//	private Double x;
//	private Double y;
	private Coordinate coordinate;
	private Double relativeDistance;
	
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
//	public Timestamp getShowingDate() {
//		return showingDate;
//	}
//	public void setShowingDate(Timestamp showingDate) {
//		this.showingDate = showingDate;
//	}

	public Calendar getShowingDate() {
		return showingDate;
	}
	public void setShowingDate(Calendar showingDate) {
		this.showingDate = showingDate;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}	
//	public Double getX() {
//		return x;
//	}
//	public void setX(Double x) {
//		this.x = x;
//	}
//	public Double getY() {
//		return y;
//	}
//	public void setY(Double y) {
//		this.y = y;
//	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public void setRelativeDistance(Double distance) {
		this.relativeDistance = distance;
	}	
	public Double getRelativeDistance() {
		return relativeDistance;
	}
	public Coordinate getCoordinate() {
		return coordinate;
	}	

}
