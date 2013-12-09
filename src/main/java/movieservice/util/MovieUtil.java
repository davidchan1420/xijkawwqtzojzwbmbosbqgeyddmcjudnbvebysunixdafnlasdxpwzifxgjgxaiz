package movieservice.util;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import movieservice.domain.SearchCriteria;

public class MovieUtil {
	
//	public static Double getRelativeDistance(SearchCriteria searchCriteria, Coordinate coordinate){
//		
//		return getRelativeDistance(searchCriteria.getX(), searchCriteria.getY(), coordinate.getX(), coordinate.getY());
//	}
//	
//	public static Double getRelativeDistance(Double x1, Double y1, Double x2, Double y2){
//				
//		Double distance = Math.sqrt(Math.pow((y2 - y1), 2D) + Math.pow(x2 - x1, 2D));
//		return distance;
//	}
	
	public static Double getRelativeDistance(SearchCriteria searchCriteria, Coordinate coordinate){
		
		return getRelativeDistance(searchCriteria.getX(), searchCriteria.getY(), coordinate.getX(), coordinate.getY());
	}
	
	public static Double getRelativeDistance(Double x1, Double y1, Double x2, Double y2){
				
		LatLng pointStart = new LatLng(x1, y1);
		LatLng pointEnd = new LatLng(x2, y2);
		
		double distanceInKM = LatLngTool.distance(pointStart, pointEnd, LengthUnit.KILOMETER);
		return distanceInKM;
	}
	
	public static void main(String[] args){
		
//		System.out.println(getRelativeDistance(9D, 10D, 18D, 18D));
		System.out.println(getRelativeDistance(22.3291015D, 114.1882631D, 22.323244D, 114.258037D));	//TKO
		System.out.println(getRelativeDistance(22.3291015D, 114.1882631D, 22.322882D, 114.212257D));	//TELFORD	
		
//		LatLng point1 = new LatLng(22.3291015D, 114.1882631D);
//		LatLng point2 = new LatLng(22.323244D, 114.258037D);
//		LatLng point3 = new LatLng(22.322882D, 114.212257D);
//		LatLng point4 = new LatLng(22.329167D, 114.192163D);		
//		
//		double distanceInMeter1 = LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
//		double distanceInMeter2 = LatLngTool.distance(point1, point3, LengthUnit.KILOMETER);
//		double distanceInMeter3 = LatLngTool.distance(point1, point4, LengthUnit.KILOMETER);
//		System.out.println("direct displacement at TKO is : " + distanceInMeter1);
//		System.out.println("direct displacement at TELFORD is : " + distanceInMeter2);
//		System.out.println("direct displacement at Kai Tak Road is : " + distanceInMeter3);
		
	}
	
	
	
}
