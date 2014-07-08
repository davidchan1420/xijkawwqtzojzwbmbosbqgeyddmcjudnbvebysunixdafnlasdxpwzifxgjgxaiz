package movieservice.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Coordinate implements Parcelable {
	
	@Override
	public int describeContents() {
		return this.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(x);
		dest.writeDouble(y);
//		dest.writeString(cinemaEnglish);
//		dest.writeString(cinemaChinese);
		dest.writeValue(cinemaEnglish);
		dest.writeValue(cinemaChinese);
	}

	public Coordinate(Parcel in) {
		
		x = in.readDouble();
		y = in.readDouble();
//		cinemaEnglish = in.readString();
//		cinemaChinese = in.readString();
		cinemaEnglish = (String) in.readValue(Coordinate.class.getClassLoader());
		cinemaChinese = (String) in.readValue(Coordinate.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Coordinate> CREATOR = new Parcelable.Creator<Coordinate>() {
		public Coordinate createFromParcel(Parcel in) {
			return new Coordinate(in);
		}

		public Coordinate[] newArray(int size) {
			return new Coordinate[size];
		}
	};	
	
	protected Double x;
	protected Double y;
	protected String cinemaEnglish;
	protected String cinemaChinese;

	public Coordinate(){}
	
	// For Comparison
	public Coordinate(String cinema) {
		this.cinemaChinese = cinema;
		this.cinemaEnglish = cinema;
	}

	// For Testing
//	public Coordinate(String cinemaChinese, String cinemaEnglish) {
//		this.cinemaChinese = cinemaChinese;
//		this.cinemaEnglish = cinemaEnglish;
//	}

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