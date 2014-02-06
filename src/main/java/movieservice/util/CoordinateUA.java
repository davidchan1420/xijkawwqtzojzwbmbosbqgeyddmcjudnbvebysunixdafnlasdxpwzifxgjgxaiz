package movieservice.util;

import android.os.Parcel;
import android.os.Parcelable;

public class CoordinateUA extends Coordinate {

	private String urlChi;
	private String urlEng;
	
	private static final String URL_UA_CHI = "http://www.cityline.com/chi/movie/byCinemaStep2.jsp?venueKey=";
	private static final String URL_UA_ENG = "http://www.cityline.com/eng/movie/byCinemaStep2.jsp?venueKey=";

	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(urlChi == null ? "" : urlChi);
		dest.writeString(urlEng == null ? "" : urlEng);
	}
	
	public CoordinateUA(Parcel in){
		super(in);
		urlChi = in.readString().equalsIgnoreCase("") ? null : in.readString();
		urlEng = in.readString().equalsIgnoreCase("") ? null : in.readString();		
	}
	
	public static final Parcelable.Creator<CoordinateUA> CREATOR = new Parcelable.Creator<CoordinateUA>() {
		public CoordinateUA createFromParcel(Parcel in) {
			return new CoordinateUA(in);
		}

		public CoordinateUA[] newArray(int size) {
			return new CoordinateUA[size];
		}
	};	
	
	// For Comparison
	public CoordinateUA(String cinema) {
		this.cinemaChinese = cinema;
		this.cinemaEnglish = cinema;
	}
	
	// Constructor
	public CoordinateUA(String cinemaChinese, String cinemaEnglish, String venueKey, Double x, Double y) {
		this.cinemaChinese = cinemaChinese;
		this.cinemaEnglish = cinemaEnglish;		
		this.urlChi = URL_UA_CHI + venueKey;
		this.urlEng = URL_UA_ENG + venueKey;		
		this.x = x;
		this.y = y;
	}

	public String getUrlChi() {
		return urlChi;
	}

	public void setUrlChi(String urlChi) {
		this.urlChi = urlChi;
	}

	public String getUrlEng() {
		return urlEng;
	}

	public void setUrlEng(String urlEng) {
		this.urlEng = urlEng;
	}

}
