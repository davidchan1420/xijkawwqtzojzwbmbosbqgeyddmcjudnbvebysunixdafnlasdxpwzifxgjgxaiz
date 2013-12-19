package movieservice.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import movieservice.domain.Movie;
import movieservice.domain.SearchCriteria;
import movieservice.service.MovieSearchService;
import movieservice.util.CalendarUtil;
import movieservice.util.ConstantUtil;
import movieservice.util.Coordinate;
import movieservice.util.CoordinateTheGrand;
import movieservice.util.CoordinateUA;
import movieservice.util.MovieUtil;

public class MovieSearchServiceImpl implements MovieSearchService {

//	private static final String urlMCLChi = "http://www2.mclcinema.com/visMovieSelect.aspx?visLang=1";
//	private static final String urlMCLEng = "http://www2.mclcinema.com/visMovieSelect.aspx?visLang=2";
//	private List<Movie> listMovie = new ArrayList<Movie>();
	
	private static final String CONSTANT_MCL = "MCL";
	private static final String CONSTANT_THE_GRAND = "THE_GRAND";
	private static final String CONSTANT_GOLDEN_HARVEST = "GOLDEN HARVEST";
	private static final String CONSTANT_BROADWAY = "BROADWAY";
	private static final String CONSTANT_AMC = "AMC";

	private static HashMap<String, HashMap<String, String>> mapURL = new HashMap<String, HashMap<String, String>>();
	private static HashMap<String, HashMap<String, Integer>> mapMonth = new HashMap<String, HashMap<String, Integer>>();
	private static HashMap<String, Integer> mapAPM = new HashMap<String, Integer>();

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

	//TODO: Check if MCL Chi Month is 1 or 01 !!!!!!!!
	public List<Movie> getMCLMovies(SearchCriteria searchCriteria) {
		URL url;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td class=\".+?\" valign=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>";
		String regCinema = "<td class=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>.+";	
		
		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\".+?\">(.+?),\\s(\\d{2}).{1}(\\d{2}).{1},\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\".+?\">(.+?),\\s(\\w{3})\\s(\\d{2}),\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_MCL).get(searchCriteria.getLanguage()));
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			String movieName = null;
			String cinema = null;
			Double relativeDistance = null;
			
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
//					 System.out.println("\t"+matCinema.group(1));
					cinema = matCinema.group(1);
					
					// Calculate Relative Distance for each Cinema
					if(searchCriteria.getDistanceRange() != null){						
						int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));
						
						if(index > -1){
							Coordinate coordinate = ConstantUtil.listCinema.get(index);
							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
						}
					}
				}
				
				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t" + matTime.group(2)+"-"+matTime.group(3)+", "+matTime.group(4)+":"+matTime.group(5)+" "+matTime.group(6)+", $"+matTime.group(7));
					Movie movie = new Movie();
					movie.setMovieName(movieName);
					movie.setCinema(cinema);
					movie.setRelativeDistance(relativeDistance);

					Calendar calMovie = CalendarUtil.getSystemCalendar();
					calMovie.set(Calendar.MONTH, mapMonth.get(searchCriteria.getLanguage()).get(matTime.group(2)));
					calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(3)));
					calMovie.set(Calendar.HOUR, Integer.parseInt(matTime.group(4)));
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(5)));
					calMovie.set(Calendar.SECOND, 0);
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(6)));

					Calendar calToday = CalendarUtil.getSystemCalendar();

					// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
					if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
						calMovie.add(Calendar.YEAR, 1);
					}

					movie.setShowingDate(calMovie.getTime());
					
					movie.setFee(Integer.parseInt(matTime.group(7)));
					listMovie.add(movie);
				}
			}
			in.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;
	}

	public List<Movie> getTheGrandMovies(SearchCriteria searchCriteria){
		
		URL url;
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
				in = new BufferedReader(new InputStreamReader(url.openStream()));

				String movieName = null;
				String cinema = null;
				Double relativeDistance = null;
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
					if (searchCriteria.getDistanceRange() != null) {
						relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordTheGrand);
					}

					Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
					Matcher matTime = patTime.matcher(inputLine);
					
					while(matTime.find()) {
//						System.out.println("\t\t"+matTime.group(1)+","+matTime.group(2)+"-"+matTime.group(3)+" "+matTime.group(4)+":"+matTime.group(5)+" "+matTime.group(6));
						Movie movie = new Movie();
						movie.setMovieName(movieName);
						movie.setCinema(cinema);
						movie.setRelativeDistance(relativeDistance);

						Calendar calMovie = CalendarUtil.getSystemCalendar();
						calMovie.set(Calendar.MONTH, mapMonth.get(searchCriteria.getLanguage()).get(matTime.group(2)));
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(3)));
						calMovie.set(Calendar.HOUR, Integer.parseInt(matTime.group(4)));
						calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(5)));
						calMovie.set(Calendar.SECOND, 0);
						calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(6)));
						
						Calendar calToday = CalendarUtil.getSystemCalendar();

						// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
						if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
							calMovie.add(Calendar.YEAR, 1);
						}
						movie.setShowingDate(calMovie.getTime());
						
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

			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
		}		
		
		return listMovie;
	}
	
	public List<Movie> getUAMovies(SearchCriteria searchCriteria) {

		URL url;
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
				in = new BufferedReader(new InputStreamReader(url.openStream()));

				String movieName = null;
				String cinema = null;
				Double relativeDistance = null;
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
						
					// Calculate Relative Distance for each Cinema
					if(searchCriteria.getDistanceRange() != null){
						
						relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordUA);					
					}				

					Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
					Matcher matTime = patTime.matcher(inputLine);
					
					while(matTime.find()) {
//						System.out.println("\t\t"+matTime.group(1)+"-"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
						Movie movie = new Movie();
						movie.setMovieName(movieName);
						movie.setCinema(cinema);
						movie.setRelativeDistance(relativeDistance);
	//
						Calendar calMovie = CalendarUtil.getSystemCalendar();
						
						calMovie.set(Calendar.MONTH, mapMonth.get(ConstantUtil.LANG_ENG).get(matTime.group(1)));	//Both Chi and Eng version uses Eng month names						
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(2)));										
						
						Integer hour = Integer.parseInt(matTime.group(3));					
						calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
						calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
						calMovie.set(Calendar.SECOND, 0);						
						calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));

						Calendar calToday = CalendarUtil.getSystemCalendar();

						// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
						if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
							calMovie.add(Calendar.YEAR, 1);
						}
						movie.setShowingDate(calMovie.getTime());
						
						movie.setFee(Integer.parseInt(matTime.group(6)));
						listMovie.add(movie);					
					}
				}
				in.close();						

			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
		}		
		
		return listMovie;
	}

	public List<Movie> getGoldenHarvestMovies(SearchCriteria searchCriteria) {

		URL url;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td align=\".+?\" class=\".+?\"><a style=\".+?\" href=\".+?\">(.+?)</a>";		
		String regCinema = "<td width=\".+?\" align=\".+?\" nowrap class=\".+?\"><a class=\".+?\" href=\".+?\">(.+?)</a></td>";		

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\".+?\">.+?,\\s(\\d{1,2}).{1}(\\d{1,2}).{1}\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\".+?\">\\w{6,9},\\s(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})\\s(\\w{2}).+?\\$(\\d{2,3})</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_GOLDEN_HARVEST).get(searchCriteria.getLanguage()));
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			String movieName = null;
			String cinema = null;
			Double relativeDistance = null;
			
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
					if(searchCriteria.getDistanceRange() != null){						
						int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));
						
						if(index > -1){
							Coordinate coordinate = ConstantUtil.listCinema.get(index);
							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
						}
					}					
				}				

				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t"+matTime.group(1)+"-"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
					Movie movie = new Movie();
					movie.setMovieName(movieName);
					movie.setCinema(cinema);
					movie.setRelativeDistance(relativeDistance);
//
					Calendar calMovie = CalendarUtil.getSystemCalendar();
					
					if(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_CHI)){
						calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(1))-1);
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(2)));	
					}else if(searchCriteria.getLanguage().equalsIgnoreCase(ConstantUtil.LANG_ENG)){
						calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(1)));
						calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(2))-1);
					}				
					
					Integer hour = Integer.parseInt(matTime.group(3));					
					calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
					calMovie.set(Calendar.SECOND, 0);
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));

					Calendar calToday = CalendarUtil.getSystemCalendar();

					// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
					if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
						calMovie.add(Calendar.YEAR, 1);
					}
					movie.setShowingDate(calMovie.getTime());
					
					movie.setFee(Integer.parseInt(matTime.group(6)));
					listMovie.add(movie);					
				}
			}
			in.close();						

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;
	}

	public List<Movie> getBroadwayMovies(SearchCriteria searchCriteria) {		

		URL url;
		BufferedReader in;
		String inputLine;
		String regMovieName = "<td width=\".+?\" valign=\".+?\"><span class=\".+?\" style=\".+?\">(.+?)</span><br /></td>";
		String regCinema = "<td width=\".+?\" class=\".+?\" valign=\".+?\"><a href=\".+?\" class=\".+?\">(.+?)</a></td>";		

		HashMap<String, String> mapRegTime = new HashMap<String, String>();
		mapRegTime.put(ConstantUtil.LANG_CHI, "<option value=\".+?\">(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");		
		mapRegTime.put(ConstantUtil.LANG_ENG, "<option value=\".+?\">(\\d{1,2})/(\\d{1,2})\\s(\\d{2}):(\\d{2})(\\w{2}).+?\\$(\\d{2,3})</option>");
		
		List<Movie> listMovie = new ArrayList<Movie>();
		try {
			url = new URL(mapURL.get(CONSTANT_BROADWAY).get(searchCriteria.getLanguage()));
			in = new BufferedReader(new InputStreamReader(url.openStream(), "Big5"));

			String movieName = null;
			String cinema = null;
			Double relativeDistance = null;
			
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
					if(searchCriteria.getDistanceRange() != null){						
						int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));
						
						if(index > -1){
							Coordinate coordinate = ConstantUtil.listCinema.get(index);
							relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
						}
					}					
				}				

				Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
				Matcher matTime = patTime.matcher(inputLine);
				if (matTime.find()) {
//					System.out.println("\t\t"+matTime.group(1)+"-"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
					Movie movie = new Movie();
					movie.setMovieName(movieName);
					movie.setCinema(cinema);
					movie.setRelativeDistance(relativeDistance);
//
					Calendar calMovie = CalendarUtil.getSystemCalendar();
					
					calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(1)));
					calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(2)) - 1);
					
					Integer hour = Integer.parseInt(matTime.group(3));					
					calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
					calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
					calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));

					Calendar calToday = CalendarUtil.getSystemCalendar();

					// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
					if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
						calMovie.add(Calendar.YEAR, 1);
					}
					movie.setShowingDate(calMovie.getTime());
					
					movie.setFee(Integer.parseInt(matTime.group(6)));
					listMovie.add(movie);					
				}
			}
			in.close();						

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;		
		
	}

	@Override
	public List<Movie> getAMCMovies(SearchCriteria searchCriteria) {

		
		URL url;
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
			in = new BufferedReader(new InputStreamReader(url.openStream(), "Big5"));

			String movieName = null;
			String cinema = null;
			Double relativeDistance = null;
			
			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine);
				
				Pattern patPreMovieName = Pattern.compile(regPreMovieName);
				Matcher matPreMovieName = patPreMovieName.matcher(inputLine);
				if (matPreMovieName.find()) {

					inputLine = in.readLine();
					Pattern patMovieName = Pattern.compile(regMovieName);
					Matcher matMovieName = patMovieName.matcher(inputLine);
					if (matMovieName.find()) {
						System.out.println(matMovieName.group(1));
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
						
						System.out.println("\t"+matCinema.group(1));
						cinema = matCinema.group(1);
						
						// Calculate Relative Distance for each Cinema
						if(searchCriteria.getDistanceRange() != null){						
							int index = ConstantUtil.listCinema.indexOf(new Coordinate(cinema));
							
							if(index > -1){
								Coordinate coordinate = ConstantUtil.listCinema.get(index);
								relativeDistance = MovieUtil.getRelativeDistance(searchCriteria, coordinate);
							}
						}
						
						while ((inputLine = in.readLine()) != null) {
						
							Pattern patPostTime = Pattern.compile(regPostTime);
							Matcher matPostTime = patPostTime.matcher(inputLine);
						
							if(!matPostTime.find()){
								
								Pattern patTime = Pattern.compile(mapRegTime.get(searchCriteria.getLanguage()));
								Matcher matTime = patTime.matcher(inputLine);
								if (matTime.find()) {
									System.out.println("\t\t"+matTime.group(1)+"/"+matTime.group(2)+" "+matTime.group(3)+":"+matTime.group(4)+" "+matTime.group(5)+" $"+matTime.group(6));
									Movie movie = new Movie();
									movie.setMovieName(movieName);
									movie.setCinema(cinema);
									movie.setRelativeDistance(relativeDistance);

									Calendar calMovie = CalendarUtil.getSystemCalendar();
									
									calMovie.set(Calendar.DATE, Integer.parseInt(matTime.group(1)));
									calMovie.set(Calendar.MONTH, Integer.parseInt(matTime.group(2)) - 1);
									
									Integer hour = Integer.parseInt(matTime.group(3));					
									calMovie.set(Calendar.HOUR, hour==12 ? 0 : hour);
									calMovie.set(Calendar.MINUTE, Integer.parseInt(matTime.group(4)));
									calMovie.set(Calendar.AM_PM, mapAPM.get(matTime.group(5)));
			
									Calendar calToday = CalendarUtil.getSystemCalendar();
			
									// If Today's month is December And the Movie's showing month is January (Next Year), set the showing year to year + 1
									if ((calToday.get(Calendar.MONTH) == Calendar.DECEMBER) && (calMovie.get(Calendar.MONTH) == Calendar.JANUARY)) {
										calMovie.add(Calendar.YEAR, 1);
									}
									movie.setShowingDate(calMovie.getTime());
									
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

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return listMovie;		
		
	}	
	
	public List<Movie> getAllMovies() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {

		SearchCriteria searchCriteria = new SearchCriteria();
//		searchCriteria.setLanguage("CHI");
		searchCriteria.setLanguage("ENG");
		searchCriteria.setDistanceRange(5000);
		searchCriteria.setX(22.3291015D);
		searchCriteria.setY(114.1882631D);
		
		MovieSearchServiceImpl instance = new MovieSearchServiceImpl();
		List<Movie> list = new ArrayList<Movie>();
		
//		List<Movie> list1 = instance.getMCLMovies(searchCriteria);
//		list.addAll(list1);
//		List<Movie> list2 = instance.getTheGrandMovies(searchCriteria);
//		list.addAll(list2);
//		List<Movie> list3 = instance.getGoldenHarvestMovies(searchCriteria);
//		list.addAll(list3);
//		List<Movie> list4 = instance.getBroadwayMovies(searchCriteria);
//		list.addAll(list4);
//		List<Movie> list5 = instance.getUAMovies(searchCriteria);
//		list.addAll(list5);b
		List<Movie> list6 = instance.getAMCMovies(searchCriteria);
		list.addAll(list6);
		
		
//		System.out.println("list1 size: " + list1.size());
//		System.out.println("list2 size: " + list2.size());
//		System.out.println("list3 size: " + list3.size());
		
		
//		for (int i = 0; i < list.size(); i++) {
//			Movie movie = list.get(i);
//			System.out.println("Movie Name: " + movie.getMovieName() + ", Cinema: " + movie.getCinema() + ", Distance: " + movie.getRelativeDistance() + ", Time: " + movie.getShowingDate() + ", Fee: $" + movie.getFee());
//		}
//
//		System.out.println("list size: " + list.size());		
		
	}



}
