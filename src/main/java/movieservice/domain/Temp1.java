package movieservice.domain;

import java.io.Serializable;

import movieservice.util.Coordinate;
import android.os.Parcel;
import android.os.Parcelable;

public class Temp1 implements Parcelable, Serializable {

	private static final long serialVersionUID = -9063117125382167569L;
	private String name;
//	private Coordinate coordinate;
	
	@Override
	public int describeContents() {
//		return this.hashCode();
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(name);
//		dest.writeParcelable(coordinate, flags);		
	}
	
	public Temp1(Parcel in){
		
		name = in.readString();
//		coordinate = in.readParcelable(Temp1.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Temp1> CREATOR = new Parcelable.Creator<Temp1>() {
		public Temp1 createFromParcel(Parcel in) {
			return new Temp1(in);
		}

		public Temp1[] newArray(int size) {
			return new Temp1[size];
		}
	};	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

//	public Coordinate getCoordinate() {
//		return coordinate;
//	}
//
//	public void setCoordinate(Coordinate coordinate) {
//		this.coordinate = coordinate;
//	}

	public Temp1(){}
	
	

}
