package movieservice.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.MovieService;
import movieservice.util.CalendarUtil;
import movieservice.util.ConstantUtil;
import movieservice.util.Coordinate;
import movieservice.util.CoordinateBroadway;
import movieservice.util.CoordinateTheGrand;
import movieservice.util.CoordinateUA;
import movieservice.util.MovieUtil;

public class MovieServiceImpl implements MovieService {

	private static final Integer READ_TIMEOUT = 5000;	//5 Seconds
	private static final String CONSTANT_MCL = "MCL";
	private static final String CONSTANT_THE_GRAND = "THE_GRAND";
	private static final String CONSTANT_GOLDEN_HARVEST = "GOLDEN HARVEST";
	private static final String CONSTANT_BROADWAY = "BROADWAY";
	private static final String CONSTANT_AMC = "AMC";

	private static HashMap<String, HashMap<String, String>> mapURL = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, HashMap<String, Integer>> mapMonth = new HashMap<String, HashMap<String, Integer>>();
	private static HashMap<String, Integer> mapAPM = new HashMap<String, Integer>();
	
	private static final String CURL_BROADWAY_AVAILABLE_SEAT = "https://www.cinema.com.hk/revamp/html/show_seat.php";
	private static final String CURL_BROADWAY_POST_PARAMETER = "show_id=";

	static {
		
		HashMap<String, String> urlMCL = new HashMap<String, String>();
		urlMCL.put(ConstantUtil.LANG_CHI, "http://www2.mclcinema.com/visMovieSelect.aspx?visLang=1");
		urlMCL.put(ConstantUtil.LANG_ENG, "http://www2.mclcinema.com/visMovieSelect.aspx?visLang=2");
		mapURL.put(CONSTANT_MCL, urlMCL);
		
		HashMap<String, String> urlTheGrand = new HashMap<String, String>();
		urlTheGrand.put(ConstantUtil.LANG_CHI, "http://www.thegrandcinema.com.hk/index.aspx?visLang=1");
		urlTheGrand.put(ConstantUtil.LANG_ENG, "http://www.thegrandcinema.com.hk/index.aspx?visLang=2");
		mapURL.put(CONSTANT_THE_GRAND, urlTheGrand);		
		
		HashMap<String, String> urlGoldenHarvest = new HashMap<String, String>();
		urlGoldenHarvest.put(ConstantUtil.LANG_CHI, "http://www.goldenharvest.com/nowshowing.aspx?lang=cht");
		urlGoldenHarvest.put(ConstantUtil.LANG_ENG, "http://www.goldenharvest.com/nowshowing.aspx?lang=eng");
		mapURL.put(CONSTANT_GOLDEN_HARVEST, urlGoldenHarvest);
		
		HashMap<String, String> urlBroadway = new HashMap<String, String>();
		urlBroadway.put(ConstantUtil.LANG_CHI, "http://www.cinema.com.hk/revamp/html/movie_ticketing.php?lang=c&mode=ticketing");
		urlBroadway.put(ConstantUtil.LANG_ENG, "http://www.cinema.com.hk/revamp/html/movie_ticketing.php?lang=e&mode=ticketing");
		mapURL.put(CONSTANT_BROADWAY, urlBroadway);
		
		HashMap<String, String> urlAMC = new HashMap<String, String>();
		urlAMC.put(ConstantUtil.LANG_CHI, "http://www.amccinemas.com.hk/tkting_upcoming.php?lang=c&mode=ticketing");
		urlAMC.put(ConstantUtil.LANG_ENG, "http://www.amccinemas.com.hk/tkting_upcoming.php?lang=e&mode=ticketing");
		mapURL.put(CONSTANT_AMC, urlAMC);
		
		
		HashMap<String, Integer> monthChi = new HashMap<String, Integer>();
		monthChi.put("1", 0);
		monthChi.put("2", 1);
		monthChi.put("3", 2);
		monthChi.put("4", 3);
		monthChi.put("5", 4);
		monthChi.put("6", 5);
		monthChi.put("7", 6);
		monthChi.put("8", 7);
		monthChi.put("9", 8);
		monthChi.put("10", 9);
		monthChi.put("11", 10);
		monthChi.put("12", 11);

		HashMap<String, Integer> monthEng = new HashMap<String, Integer>();
		monthEng.put("Jan", 0);
		monthEng.put("Feb", 1);
		monthEng.put("Mar", 2);
		monthEng.put("Apr", 3);
		monthEng.put("May", 4);
		monthEng.put("Jun", 5);
		monthEng.put("Jul", 6);
		monthEng.put("Aug", 7);
		monthEng.put("Sep", 8);
		monthEng.put("Oct", 9);
		monthEng.put("Nov", 10);
		monthEng.put("Dec", 11);

		mapMonth.put(ConstantUtil.LANG_CHI, monthChi);
		mapMonth.put(ConstantUtil.LANG_ENG, monthEng);

		mapAPM.put("AM", Calendar.AM);
		mapAPM.put("PM", Calendar.PM);
	}

	public List<Movie> getMCLMovies(SearchCriteria searchCriteria) {
		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td class=\".+?\" valign=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>";
		String regCinema = "<td class=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>.+";	
		
		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\".+?\">(.+?),\\s(\\d{1,2}).{1}(\\d{2}).{1},\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\".+?\">(.+?),\\s(\\w{3})\\s(\\d{2}),\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_MCL).get(searchCriteria.getLanguage()));			
			connection = url.openConnection();
			connection.setReadTimeout(READ_TIMEOUT);
			
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));		

			String movieName = null;
			String cinema = null;
//			Double relativeDistance = null;
			Coordinate coordinate = null;
			
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				Pattern patMovieName = Pattern.compile(regMovieName);
				Matcher matMovieName = patMovieName.matcher(inputLine);
				if (matMovieName.find()) {
//					 System.out.println(matMovieName.group(1));
					movieName = matMovieName.group(1);
				}
				
				Pattern patCinema = Pattern.compile(regCinema);
				Matcher matCinema = patCinema.matcher(inputLine);
				if (matCinema.find()) {
//					System.out.println("\t"+matCinema.group(1));
					cinema = matCinema.group(1);
					
					// Calculate Relative Distance for each Cinema
//					if(searchCriteria.getDistanceRange() != null){
						int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));						
						if(index > -1){
//							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
							coordinate = ConstantUtil.listCinema.get(index);
						}
//					}
				}
				
				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t" + matTime.group(2)+"-"+matTime.group(3)+", "+matTime.group(4)+":"+matTime.group(5)+" "+matTime.group(6)+", $"+matTime.group(7));
					Movie movie = new Movie();
					movie.setMovieName(movieName);
					movie.setCinema(cinema);
					
//					movie.setRelativeDistance(relativeDistance);
//					movie.setX(coordinate.getX());
//					movie.setY(coordinate.getY());
					movie.setCoordinate(coordinate);					

					Calendar calMovie = CalendarUtil.getSystemCalendar();
					calMovie.set(Calendar.MONTH, mapMonth.get(searchCriteria.getLanguage()).get(matTime.group(2)));					
					calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(3)));					
					
					Integer hour = Integer.parseInt(matTime.group(4));					
					calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);					
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(5)));
					calMovie.set(Calendar.SECOND, 0);
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(6)));

					Calendar calToday = CalendarUtil.getSystemCalendar();

					checkNextYear(calToday, calMovie);

					movie.setShowingDate(calMovie);
					
					movie.setFee(Integer.parseInt(matTime.group(7)));
					listMovie.add(movie);
//					System.out.println(calMovie);
				}
			}
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;
	}

	public List<Movie> getTheGrandMovies(SearchCriteria searchCriteria){
		
		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td class=\".+?\" height=\".+?\" width=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>.+?";		
		
		HashMap<String, String> mapRegTime = new HashMap<String, String>();			
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\"(.+?)\">.+?(\\d{1,2}).+?(\\d{1,2}).+?(\\d{2}):(\\d{2})(\\w{2}).+?</option>");		
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\"(.+?)\">.+?,\\s(\\w{3})\\s(\\d{1,2}),\\s(\\d{2}):(\\d{2})(\\w{2}).+?</option>");
				
		List<Movie> listMovie = new ArrayList<Movie>();
		
		for(int i=0; i < ConstantUtil.listCinemaTheGrand.size(); i++){
			
			CoordinateTheGrand coordTheGrand = ConstantUtil.listCinemaTheGrand.get(i);
		
			try {				
				url = new URL(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? coordTheGrand.getUrlChi() : coordTheGrand.getUrlEng());
				connection = url.openConnection();
				connection.setReadTimeout(READ_TIMEOUT);
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String movieName = null;
				String cinema = null;
//				Double relativeDistance = null;
//				System.out.println(url.toExternalForm());

				while ((inputLine = in.readLine()) != null) {
//					System.out.println(inputLine);
					Pattern patMovieName = Pattern.compile(regMovieName);
					Matcher matMovieName = patMovieName.matcher(inputLine);
					if (matMovieName.find()) {
//						System.out.println(matMovieName.group(1));
						movieName = matMovieName.group(1);						
					}

					cinema = searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? coordTheGrand.getCinemaChinese() : coordTheGrand.getCinemaEnglish();
						
					// Calculate Relative Distance for The Grand Cinema
//					if (searchCriteria.getDistanceRange() != null) {
//						relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordTheGrand);
//					}

					Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
					Matcher matTime = patTime.matcher(inputLine);
					
					while(matTime.find()) {
//						System.out.println("\t\t"+matTime.group(1)+","+matTime.group(2)+"-"+matTime.group(3)+" "+matTime.group(4)+":"+matTime.group(5)+" "+matTime.group(6));
						Movie movie = new Movie();
						movie.setMovieName(movieName);
						movie.setCinema(cinema);
//						movie.setRelativeDistance(relativeDistance);
						movie.setCoordinate(coordTheGrand);						

						Calendar calMovie = CalendarUtil.getSystemCalendar();
						calMovie.set(Calendar.MONTH, mapMonth.get(searchCriteria.getLanguage()).get(matTime.group(2)));
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(3)));
						
						Integer hour = Integer.parseInt(matTime.group(4));					
						calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);						
						calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(5)));
						calMovie.set(Calendar.SECOND, 0);
						calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(6)));
						
						Calendar calToday = CalendarUtil.getSystemCalendar();

						checkNextYear(calToday, calMovie);
						
						movie.setShowingDate(calMovie);
						
						//TODO: We don't open this URL to get the fee because it will greatly slow down the speed						
//						StringBuilder sb = new StringBuilder();
//						sb.append(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? CoordinateTheGrand.URL_FEE_CHI : CoordinateTheGrand.URL_FEE_ENG);
//						sb.append(matTime.group(1));						
//						URL urlFee = new URL(sb.toString());
//						System.out.println(urlFee.toExternalForm());
						
//						BufferedReader inFee = new BufferedReader(new InputStreamReader(urlFee.openStream()));
//						String inputLineFee;//						
//						if((inputLineFee = inFee.readLine()) != null) {
//							System.out.println(inputLineFee);
//						}
						
//						movie.setFee(Integer.parseInt(matTime.group(X)));
						listMovie.add(movie);
					}
					
				}
				in.close();						

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}		
		
		return listMovie;
	}
	
	public List<Movie> getUAMovies(SearchCriteria searchCriteria) {

		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td width=\".+?\"><a href=\".+?\"><font class=\".+?\">(.+?)</font></a></td>";		

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<OPTION.+?>.+?,\\s(\\w{3})\\s(\\d{2}),\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3}).+?</OPTION>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<OPTION.+?>.+?,\\s(\\w{3})\\s(\\d{2}),\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3}).+?</OPTION>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		
		for(int i=0; i < ConstantUtil.listCinemaUA.size(); i++){
			
			CoordinateUA coordUA = ConstantUtil.listCinemaUA.get(i);
		
			try {				
				url = new URL(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? coordUA.getUrlChi() : coordUA.getUrlEng());
				connection = url.openConnection();
				connection.setReadTimeout(READ_TIMEOUT);
				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String movieName = null;
				String cinema = null;
//				Double relativeDistance = null;
//				System.out.println(url.toExternalForm());
				while ((inputLine = in.readLine()) != null) {
					
//					System.out.println(inputLine);
					Pattern patMovieName = Pattern.compile(regMovieName);
					Matcher matMovieName = patMovieName.matcher(inputLine);
					if (matMovieName.find()) {
//						System.out.println(matMovieName.group(1));
						movieName = matMovieName.group(1);
					}

					cinema = searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? coordUA.getCinemaChinese() : coordUA.getCinemaEnglish();
//					System.out.println(cinema);					
					
					// Calculate Relative Distance for each Cinema
//					if(searchCriteria.getDistanceRange() != null){						
//						relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordUA);					
//					}				

					Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
					Matcher matTime = patTime.matcher(inputLine);
					
					while(matTime.find()) {
//						System.out.println("\t\t"+matTime.group(1)+"-"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
						Movie movie = new Movie();
						movie.setMovieName(movieName);
						movie.setCinema(cinema);
//						movie.setRelativeDistance(relativeDistance);
						movie.setCoordinate(coordUA);						

						Calendar calMovie = CalendarUtil.getSystemCalendar();
						
						calMovie.set(Calendar.MONTH, mapMonth.get(ConstantUtil.LANG_ENG).get(matTime.group(1)));	//Both Chi and Eng version uses Eng month names						
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(2)));										
						
						Integer hour = Integer.parseInt(matTime.group(3));					
						calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
						calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
						calMovie.set(Calendar.SECOND, 0);						
						calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));

						Calendar calToday = CalendarUtil.getSystemCalendar();

						checkNextYear(calToday, calMovie);
						
						movie.setShowingDate(calMovie);
						
						movie.setFee(Integer.parseInt(matTime.group(6)));
						listMovie.add(movie);					
					}
				}
				in.close();						

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}		
		
		return listMovie;
	}

	public List<Movie> getGoldenHarvestMovies(SearchCriteria searchCriteria) {

		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		
		String regMovieName = "<td align=\".+?\" class=\".+?\"><a style=\".+?\" href=\".+?\">(.+?)</a>";		
//		String regCinema = "<td width=\".+?\" align=\".+?\" nowrap class=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>";		
		String regCinema = "<a class=\".+?\" href=\".+?\">(.+?)</a>";

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\".+?\">.+?,\\s(\\d{1,2}).{1}(\\d{1,2}).{1}\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3}).*?</option>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\".+?\">\\w{6,9},\\s(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3}).*?</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_GOLDEN_HARVEST).get(searchCriteria.getLanguage()));
			connection = url.openConnection();
			connection.setReadTimeout(READ_TIMEOUT);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String movieName = null;
			String cinema = null;
//			Double relativeDistance = null;
			Coordinate coordinate = null;
			
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				Pattern patMovieName = Pattern.compile(regMovieName);
				Matcher matMovieName = patMovieName.matcher(inputLine);
				if (matMovieName.find()) {
//					System.out.println(matMovieName.group(1));
					
					String unFiltered = matMovieName.group(1);
//					int index = unFiltered.indexOf("<br/>");
//					if(index != -1){						
//						movieName = unFiltered.substring(0, index);						
//					}else{
//						movieName = unFiltered;	
//					}
					movieName = unFiltered.replace("<br/>", " ");
				}

				Pattern patCinema = Pattern.compile(regCinema);
				Matcher matCinema = patCinema.matcher(inputLine);				
				if (matCinema.find()) {
//					System.out.println("\t"+matCinema.group(1));
					cinema = matCinema.group(1);
					
					// Calculate Relative Distance for each Cinema
//					if(searchCriteria.getDistanceRange() != null){						
						int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));						
						if(index > -1){
							coordinate = ConstantUtil.listCinema.get(index);
//							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
						}
//					}					
				}				

				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t"+matTime.group(1)+"-"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
					Movie movie = new Movie();
					movie.setMovieName(movieName);
					movie.setCinema(cinema);
//					movie.setRelativeDistance(relativeDistance);
					movie.setCoordinate(coordinate);					

					Calendar calMovie = CalendarUtil.getSystemCalendar();					
					
					if(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI)){
						calMovie.set(Calendar.MONTH, mapMonth.get(ConstantUtil.LANG_CHI).get(matTime.group(1)));						
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(2)));	
						
					}else if(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_ENG)){
						calMovie.set(Calendar.MONTH, mapMonth.get(ConstantUtil.LANG_CHI).get(matTime.group(2)));						
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(1)));							
					}				
					
					Integer hour = Integer.parseInt(matTime.group(3));					
					calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
					calMovie.set(Calendar.SECOND, 0);
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));

					Calendar calToday = CalendarUtil.getSystemCalendar();

					checkNextYear(calToday, calMovie);
					
					movie.setShowingDate(calMovie);
					
					movie.setFee(Integer.parseInt(matTime.group(6)));
					listMovie.add(movie);					
				}
			}
			in.close();						

		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;
	}
	
	//TODO: Abandoned because the HttpClient API overlapped with the one in Android
//	public static BufferedReader curlBroadwayAvailableSeat(String showId) {
//
//		BufferedReader in = null;
//
//		HttpClient httpClient = HttpClientBuilder.create().build();		
////		httpClient.getParams().setParameter("http.socket.timeout", timeout * 1000);
////		httpClient.getParams().setParameter("http.connection.timeout", timeout * 1000);
////		httpClient.getParams().setParameter("http.connection-manager.timeout", new Long(timeout * 1000));
////		httpClient.getParams().setParameter("http.protocol.head-body-timeout", timeout * 1000);		
//
//		// Request configuration can be overridden at the request level.
//		// They will take precedence over the one set at the client level.
//		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
//		requestConfigBuilder.setSocketTimeout(READ_TIMEOUT);
//		requestConfigBuilder.setConnectTimeout(READ_TIMEOUT);
//		requestConfigBuilder.setConnectionRequestTimeout(READ_TIMEOUT);
//		
//		RequestConfig requestConfig = requestConfigBuilder.build();
//		HttpPost httpPost = new HttpPost(CURL_BROADWAY_AVAILABLE_SEAT);
//		httpPost.setConfig(requestConfig);
//		
//		// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		// nameValuePairs.add(new BasicNameValuePair("data", data));
//
//		try {
//			
//			// httpPost.setHeader("Accept",
//			// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//			// httpPost.setHeader("Accept-Encoding", "gzip,deflate");
//			// httpPost.setHeader("Accept-Language", "en-US,en;q=0.8,zh-TW;q=0.6,zh;q=0.4");
//
//			// add the param to postRequest
//			// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			httpPost.setEntity(new StringEntity(CURL_BROADWAY_POST_PARAMETER + showId));
//
//			// obtain the response
//			HttpResponse response = httpClient.execute(httpPost);
//			
//			HttpEntity entity = response.getEntity();
//	
//			in = new BufferedReader(new InputStreamReader(entity.getContent(), "Big5"));
//
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (SocketTimeoutException e){
//			e.printStackTrace();
//		} catch (ConnectTimeoutException e){
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		
//		return in;
//	}
	
	public static BufferedReader curlBroadwayAvailableSeat(String showId) {

		BufferedReader in = null;		
		
		try {
			
			URL obj = new URL(CURL_BROADWAY_AVAILABLE_SEAT);
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setConnectTimeout(READ_TIMEOUT);
			con.setReadTimeout(READ_TIMEOUT);

			// add request header
			con.setRequestMethod("POST");
			// con.setRequestProperty("User-Agent", "Mozilla/5.0");
			// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			String urlParameters = CURL_BROADWAY_POST_PARAMETER + showId;
			
			// Send post request
			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "Big5"));

//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCode);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		

		return in;
	}
	
	public List<Movie> getBroadwayMovies(SearchCriteria searchCriteria) {		

		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td width=\".+?\" valign=\".+?\"><span class=\".+?\" style=\".+?\">(.+?)</span><br /></td>";
		String regCinema = "<td width=\".+?\" class=\".+?\" valign=\".+?\"><a href=\".+?\" class=\".+?\">(.+?)</a></td>";		

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\"(\\d+?)\">(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");		
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\"(\\d+?)\">(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_BROADWAY).get(searchCriteria.getLanguage()));
			connection = url.openConnection();
			connection.setReadTimeout(READ_TIMEOUT);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), ConstantUtil.BIG5));

			String movieName = null;
			String cinema = null;
//			Double relativeDistance = null;
			CoordinateBroadway coordinateBroadway = null;
			
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				Pattern patMovieName = Pattern.compile(regMovieName);
				Matcher matMovieName = patMovieName.matcher(inputLine);
				if (matMovieName.find()) {
//					System.out.println(matMovieName.group(1));
					movieName = matMovieName.group(1);
				}

				Pattern patCinema = Pattern.compile(regCinema);
				Matcher matCinema = patCinema.matcher(inputLine);				
				if (matCinema.find()) {
//					System.out.println("\t"+matCinema.group(1));
					cinema = matCinema.group(1);
					
					// Calculate Relative Distance for each Cinema
//					if(searchCriteria.getDistanceRange() != null){						
						int index = ConstantUtil.listCinemaBroadway.indexOf(new CoordinateBroadway(cinema));						
						if(index > -1){
							coordinateBroadway = ConstantUtil.listCinemaBroadway.get(index);
//							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
						}
//					}					
				}				

				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t"+matTime.group(1)+"\t\t"+matTime.group(2)+"-"+matTime.group(3)+" "+matTime.group(4)+":"+matTime.group(5)+" "+matTime.group(6)+" $"+matTime.group(7));
					Movie movie = new Movie();										
					
					movie.setMovieName(movieName);
//					movie.setCinema(cinema);					
					movie.setCinema(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI) ? coordinateBroadway.getDisplayNameChinese() : coordinateBroadway.getDisplayNameEnglish());
					
//					movie.setRelativeDistance(relativeDistance);
					movie.setCoordinate(coordinateBroadway);
					
					BufferedReader inCurl = curlBroadwayAvailableSeat(matTime.group(1));
					
					String inputLineCurl;
					
					if(inCurl != null){
					
						int countNormalSeat = 0, countVibratingSeat = 0, countWheelChairSeat = 0;			
						
						try {
							
//							int i = 0;							
							Pattern patNormalSeat = Pattern.compile("<td class='seat_A'  >\\d{1,2}</td>");
							Pattern patVibratingSeat = Pattern.compile("<td class='seat_AS'  >\\d{1,2}</td>");				
							Pattern patWheelChairSeat = Pattern.compile("<td class='seat_buy'>\\d{1,2}</td>");
							
							while ((inputLineCurl = inCurl.readLine()) != null) {
						
//								System.out.println(i++ + "---" + inputLineCurl);								
								Matcher matNormalSeat = patNormalSeat.matcher(inputLineCurl);
								Matcher matVibratingSeat = patVibratingSeat.matcher(inputLineCurl);					
								Matcher matWheelChairSeat = patWheelChairSeat.matcher(inputLineCurl);
								
								while(matNormalSeat.find()) {									
									
									countNormalSeat++;
								}
								while(matVibratingSeat.find()) {
									
									countVibratingSeat++;
								}
								while(matWheelChairSeat.find()) {
									
									countWheelChairSeat++;
								}								
							}
							
							movie.setNormalSeat(countNormalSeat);
							movie.setVibratingSeat(countVibratingSeat);
							movie.setWheelChairSeat(countWheelChairSeat);							
							
							inCurl.close();
						} catch (IOException e) {
							movie.setNormalSeat(null);
							movie.setVibratingSeat(null);
							movie.setWheelChairSeat(null);
							e.printStackTrace();
						}		
						
						System.out.println("Movie Name: " + movieName + ", Cinema Name: " + cinema);
						
						System.out.println("Normal Seat: " + movie.getNormalSeat());
						System.out.println("Vibrating Seat: " + movie.getVibratingSeat());
						System.out.println("Wheel Chair Seat: " + movie.getWheelChairSeat());
					}
					

					Calendar calMovie = CalendarUtil.getSystemCalendar();
					
					calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(2)));
					calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(3)) - 1);
					
					Integer hour = Integer.parseInt(matTime.group(4));
					calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(5)));
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(6)));

					Calendar calToday = CalendarUtil.getSystemCalendar();
					
					checkNextYear(calToday, calMovie);

					movie.setShowingDate(calMovie);
					
					System.out.println("Showing Date: " + calMovie.getTime());
					
					movie.setFee(Integer.parseInt(matTime.group(7)));
					
					listMovie.add(movie);					
				}
			}
			in.close();						

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;		
		
	}

	@Override
	public List<Movie> getAMCMovies(SearchCriteria searchCriteria) {
		
		URL url;
		URLConnection connection;
		BufferedReader in;
		String inputLine;
		String regPreMovieName = "<td width=\"34%\" valign=\"top\">";
		String regMovieName = "\t{8}(.+?)\t{6}";
		
		String regPreCinema = "<font color=\"#575555\">";		
		String regCinema = "<a href=\".+?\" class=\".+?\">(.+?)</a>";

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=.+?>(\\d{2})/(\\d{2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=.+?>(\\d{2})/(\\d{2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");	
		
		String regPostTime = "</select>";
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_AMC).get(searchCriteria.getLanguage()));
			connection = url.openConnection();
			connection.setReadTimeout(READ_TIMEOUT);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), ConstantUtil.BIG5));

			String movieName = null;
			String cinema = null;
//			Double relativeDistance = null;
			Coordinate coordinate = null;
			
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);				
				Pattern patPreMovieName = Pattern.compile(regPreMovieName);
				Matcher matPreMovieName = patPreMovieName.matcher(inputLine);
				if (matPreMovieName.find()) {

					inputLine = in.readLine();
					Pattern patMovieName = Pattern.compile(regMovieName);
					Matcher matMovieName = patMovieName.matcher(inputLine);
					if (matMovieName.find()) {
//						System.out.println(matMovieName.group(1));
						movieName = matMovieName.group(1);
					}					
				}				

				Pattern patPreCinema = Pattern.compile(regPreCinema);
				Matcher matPreCinema = patPreCinema.matcher(inputLine);
				
				if(matPreCinema.find()){
					
					inputLine = in.readLine();					
					Pattern patCinema = Pattern.compile(regCinema);
					Matcher matCinema = patCinema.matcher(inputLine);
										
					if (matCinema.find()) {						
//						System.out.println("\t"+matCinema.group(1));
						cinema = matCinema.group(1);
						
						// Calculate Relative Distance for each Cinema
//						if(searchCriteria.getDistanceRange() != null){						
							int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));							
							if(index > -1){
								coordinate = ConstantUtil.listCinema.get(index);
//								relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
							}
//						}
						
						while ((inputLine = in.readLine()) != null) {
						
							Pattern patPostTime = Pattern.compile(regPostTime);
							Matcher matPostTime = patPostTime.matcher(inputLine);
						
							if(!matPostTime.find()){
								
								Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
								Matcher matTime = patTime.matcher(inputLine);
								if (matTime.find()) {
//									System.out.println("\t\t"+matTime.group(1)+"/"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
									Movie movie = new Movie();
									movie.setMovieName(movieName);
									movie.setCinema(cinema);
//									movie.setRelativeDistance(relativeDistance);
									movie.setCoordinate(coordinate);

									Calendar calMovie = CalendarUtil.getSystemCalendar();
									
									calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(1)));
									calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(2)) - 1);
									
									Integer hour = Integer.parseInt(matTime.group(3));					
									calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
									calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
									calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));
			
									Calendar calToday = CalendarUtil.getSystemCalendar();
			
									checkNextYear(calToday, calMovie);
									
									movie.setShowingDate(calMovie);
									
									movie.setFee(Integer.parseInt(matTime.group(6)));
									listMovie.add(movie);					
								}								
							}else{
								break;
							}
						}						
					}
				}				
			}
			in.close();						

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;		
		
	}	
	
	public List<Movie> getAllMovies(SearchCriteria searchCriteria) {
		
		MovieServiceImpl instance = new MovieServiceImpl();
		List<Movie> list = new ArrayList<Movie>();
		
//		List<Movie> list1 = instance.getMCLMovies(searchCriteria);
//		if (list1 != null) {
//			list.addAll(list1);
//		}
//		System.out.println("Finished MCL...");
//		
//		List<Movie> list2 = instance.getTheGrandMovies(searchCriteria);
//		if (list2 != null) {
//			list.addAll(list2);
//		}
//		System.out.println("Finished The Grand...");
//		
//		List<Movie> list3 = instance.getUAMovies(searchCriteria);
//		if (list3 != null) {
//			list.addAll(list3);
//		}
//		System.out.println("Finished UA...");
//		
//		List<Movie> list4 = instance.getGoldenHarvestMovies(searchCriteria);
//		if (list4 != null) {
//			list.addAll(list4);
//		}
//		System.out.println("Finished Golden Harvest...");
		
		List<Movie> list5 = instance.getBroadwayMovies(searchCriteria);
		if (list5 != null) {
			list.addAll(list5);
		}
		System.out.println("Finished Broadway...");
		
//		List<Movie> list6 = instance.getAMCMovies(searchCriteria);
//		if (list6 != null) {
//			list.addAll(list6);
//		}
//		System.out.println("Finished AMC...");
	
		return list;		
	}

	private static void checkNextYear(final Calendar calToday, Calendar calMovie){		
		// If Today's month is December And the Movie's showing month is NOT December (Must be any month in next year then),
		// set the showing year to year + 1
		if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) != Calendar.DECEMBER)) {
			calMovie.add(Calendar.YEAR, 1);
		}
	}

	public static void main(String[] args) {

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setLanguage(ConstantUtil.LANG_CHI);
//		searchCriteria.setLanguage(ConstantUtil.LANG_ENG);
//		searchCriteria.setDistanceRange(5000);
//		searchCriteria.setX(22.3291015D);
//		searchCriteria.setY(114.1882631D);	
		
		MovieServiceImpl searchService = new MovieServiceImpl();
		List<Movie> list = searchService.getAllMovies(searchCriteria);
		
		for (int i = 0; i < list.size(); i++) {
			Movie movie = list.get(i);
			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Coordinate : " + movie.getCoordinate().getX() + ", " + movie.getCoordinate().getY() + ", Time: " + movie.getShowingDate().getTime() + ", Fee: $" + movie.getFee());
		}

		System.out.println("list size: " + list.size());		
		
	}



}
