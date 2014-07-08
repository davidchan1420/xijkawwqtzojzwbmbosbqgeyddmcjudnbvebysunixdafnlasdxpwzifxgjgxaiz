package movieservice.util;

import android.os.Parcel;
import android.os.Parcelable;

public class CoordinateBroadway extends Coordinate{

	private String displayNameEnglish;
	private String displayNameChinese;
	
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
//		dest.writeString(displayNameEnglish == null ? "" : displayNameEnglish);
//		dest.writeString(displayNameChinese == null ? "" : displayNameChinese);
		dest.writeValue(displayNameEnglish);
		dest.writeValue(displayNameChinese);
		
	}
	
	public CoordinateBroadway(Parcel in){
		super(in);
//		displayNameEnglish = in.readString().equalsIgnoreCase("") ? null : in.readString();
//		displayNameChinese = in.readString().equalsIgnoreCase("") ? null : in.readString();
		displayNameEnglish = (String) in.readValue(CoordinateBroadway.class.getClassLoader());
		displayNameChinese = (String) in.readValue(CoordinateBroadway.class.getClassLoader());
		
	}
	
	public static final Parcelable.Creator<CoordinateBroadway> CREATOR = new Parcelable.Creator<CoordinateBroadway>() {
		public CoordinateBroadway createFromParcel(Parcel in) {
			return new CoordinateBroadway(in);
		}

		public CoordinateBroadway[] newArray(int size) {
			return new CoordinateBroadway[size];
		}
	};	
	
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