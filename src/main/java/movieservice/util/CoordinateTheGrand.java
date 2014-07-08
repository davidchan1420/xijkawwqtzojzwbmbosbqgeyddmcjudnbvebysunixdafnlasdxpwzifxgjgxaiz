package movieservice.util;

import android.os.Parcel;
import android.os.Parcelable;

public class CoordinateTheGrand extends Coordinate {

	private String urlChi;
	private String urlEng;
	
	private static final String URL_THE_GRAND_CHI = "http://www.thegrandcinema.com.hk/index.aspx?visLang=1";
	private static final String URL_THE_GRAND_ENG = "http://www.thegrandcinema.com.hk/index.aspx?visLang=2";
	
	//TODO: We don't open this URL to get the fee because it will greatly slow down the speed
//	public static final String URL_FEE_CHI = "https://www.thegrandcinema.com.hk/visSelectTickets.aspx?visLang=1&cinemacode=009&txtSessionId=";
//	public static final String URL_FEE_ENG = "https://www.thegrandcinema.com.hk/visSelectTickets.aspx?visLang=2&cinemacode=009&txtSessionId=";
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
//		dest.writeString(urlChi == null ? "" : urlChi);
//		dest.writeString(urlEng == null ? "" : urlEng);
		dest.writeValue(urlChi);
		dest.writeValue(urlEng);
	}
	
	public CoordinateTheGrand(Parcel in){
		super(in);
//		urlChi = in.readString().equalsIgnoreCase("") ? null : in.readString();
//		urlEng = in.readString().equalsIgnoreCase("") ? null : in.readString();
		urlChi = (String) in.readValue(CoordinateTheGrand.class.getClassLoader());
		urlEng = (String) in.readValue(CoordinateTheGrand.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<CoordinateTheGrand> CREATOR = new Parcelable.Creator<CoordinateTheGrand>() {
		public CoordinateTheGrand createFromParcel(Parcel in) {
			return new CoordinateTheGrand(in);
		}

		public CoordinateTheGrand[] newArray(int size) {
			return new CoordinateTheGrand[size];
		}
	};	
	
	
	// For Comparison
//	public CoordinateTheGrand(String cinema) {
//		this.cinemaChinese = cinema;
//		this.cinemaEnglish = cinema;
//	}
	
	// Constructor
	public CoordinateTheGrand(String cinemaChinese, String cinemaEnglish, Double x, Double y) {
		this.cinemaChinese = cinemaChinese;
		this.cinemaEnglish = cinemaEnglish;		
		this.urlChi = URL_THE_GRAND_CHI;
		this.urlEng = URL_THE_GRAND_ENG;		
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