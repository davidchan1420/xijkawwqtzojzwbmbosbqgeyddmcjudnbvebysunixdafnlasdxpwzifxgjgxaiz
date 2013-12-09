package movieservice.util;

public class Coordinate {
	private Double x;
	private Double y;
	private String cinemaEnglish;
	private String cinemaChinese;

	// For Testing
	public Coordinate(String cinema) {
		this.cinemaChinese = cinema;
		this.cinemaEnglish = cinema;
	}

	// For Testing
	public Coordinate(String cinemaChinese, String cinemaEnglish) {
		this.cinemaChinese = cinemaChinese;
		this.cinemaEnglish = cinemaEnglish;
	}

	// Constructor
	public Coordinate(String cinemaChinese, String cinemaEnglish, Double x, Double y) {
		this.cinemaChinese = cinemaChinese;
		this.cinemaEnglish = cinemaEnglish;
		this.x = x;
		this.y = y;
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

	public String getCinemaEnglish() {
		return cinemaEnglish;
	}

	public void setCinemaEnglish(String cinemaEnglish) {
		this.cinemaEnglish = cinemaEnglish;
	}

	public String getCinemaChinese() {
		return cinemaChinese;
	}

	public void setCinemaChinese(String cinemaChinese) {
		this.cinemaChinese = cinemaChinese;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cinemaChinese == null) ? 0 : cinemaChinese.hashCode());
		result = prime * result + ((cinemaEnglish == null) ? 0 : cinemaEnglish.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;

		if (cinemaChinese.equalsIgnoreCase(other.cinemaChinese)) {
			return true;
		} else if (cinemaEnglish.equalsIgnoreCase(other.cinemaEnglish)) {
			return true;
		}
		return false;
	}

}