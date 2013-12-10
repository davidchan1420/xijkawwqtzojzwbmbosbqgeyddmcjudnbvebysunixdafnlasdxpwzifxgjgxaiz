package movieservice.domain;

import java.util.Date;

public class Movie {
	
	private String movieName;
	private String cinema;
	private Date showingDate;
	private Integer fee;
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

	public Date getShowingDate() {
		return showingDate;
	}
	public void setShowingDate(Date showingDate) {
		this.showingDate = showingDate;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Double getRelativeDistance() {
		return relativeDistance;
	}
	public void setRelativeDistance(Double distance) {
		this.relativeDistance = distance;
	}
	
	

}
