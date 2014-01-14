package movieservice.util;

public class CoordinateBroadway extends Coordinate{

	private String displayNameEnglish;
	private String displayNameChinese;
	
	
	public CoordinateBroadway(){}
	
	// For Comparison
	public CoordinateBroadway(String cinema) {
		this.cinemaChinese = cinema;
		this.cinemaEnglish = cinema;
	}

	// Constructor
	public CoordinateBroadway(String cinemaChinese, String cinemaEnglish, String displayNameChinese, String displayNameEnglish, Double x, Double y) {
		this.cinemaChinese = cinemaChinese;
		this.cinemaEnglish = cinemaEnglish;		
		this.x = x;
		this.y = y;
		this.displayNameChinese = displayNameChinese;
		this.displayNameEnglish = displayNameEnglish;
	}
	
	public String getDisplayNameEnglish() {
		return displayNameEnglish;
	}

	public void setDisplayNameEnglish(String displayNameEnglish) {
		this.displayNameEnglish = displayNameEnglish;
	}

	public String getDisplayNameChinese() {
		return displayNameChinese;
	}

	public void setDisplayNameChinese(String displayNameChinese) {
		this.displayNameChinese = displayNameChinese;
	}
	

}