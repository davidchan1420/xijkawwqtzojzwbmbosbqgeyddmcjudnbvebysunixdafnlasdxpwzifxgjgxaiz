package movieservice.util;

import java.util.ArrayList;

public class ConstantUtil {
	
	public static final String LANG_CHI = "CHI";
	public static final String LANG_ENG = "ENG";
	
	private static final String MCL = "MCL";	
	
	//public static HashMap<String, ArrayList<Coordinate>> mapCinema = new HashMap<String, ArrayList<Coordinate>>();
	public static final ArrayList<Coordinate> listCinema = new ArrayList<Coordinate>();		
	
	static{
		
		listCinema.add(new Coordinate("MCL JP銅鑼灣戲院", "MCL JP Cinema", 22.280715D, 114.185725D));
		listCinema.add(new Coordinate("MCL 康怡戲院", "MCL Kornhill Cinema", 22.28422D,114.216409D));
		listCinema.add(new Coordinate("MCL 將軍澳戲院 (寶琳站)", "MCL Metro Cinema (Po Lam Station)", 22.323244D, 114.258037D));		
		listCinema.add(new Coordinate("MCL 德福戲院", "MCL Telford Cinema", 22.322882D, 114.212257D));		
		listCinema.add(new Coordinate("STAR Cinema (將軍澳站)", "STAR Cinema (Tseung Kwan O Station)", 22.308468D, 114.260551D));
		
		listCinema.add(new Coordinate("海運戲院 (超巨幕)", "Grand Ocean (Xtreme)", 22.295256D, 114.169377D));		
		listCinema.add(new Coordinate("嘉禾港威", "Golden Gateway", 22.299378D, 114.167961D));		
		listCinema.add(new Coordinate("嘉禾青衣", "GH Tsing Yi", 22.359338D, 114.108255D));		
		listCinema.add(new Coordinate("嘉禾荃新天地", "GH Citywalk", 22.367732D, 114.114832D));
		listCinema.add(new Coordinate("嘉禾黃埔", "GH Whampoa", 22.304686D, 114.19035D));
		
		listCinema.add(new Coordinate("PALACE ifc", "PALACE ifc", 22.285537D, 114.158155D));
		listCinema.add(new Coordinate("數碼港", "CYBERPORT", 22.261037D, 114.129453D));		
		listCinema.add(new Coordinate("荷里活", "HOLLYWOOD", 22.340604D, 114.202092D));		
		listCinema.add(new Coordinate("The ONE", "The ONE", 22.29979D, 114.172172D));		
		listCinema.add(new Coordinate("電影中心", "CINEMATHEQUE", 22.310694D, 114.168913D));		
		listCinema.add(new Coordinate("旺角", "MONGKOK", 22.317116D, 114.170627D));		
		listCinema.add(new Coordinate("PALACE  apm", "PALACE  apm", 22.312299D, 114.225355D));		
		listCinema.add(new Coordinate("葵芳", "KWAI FONG", 22.357456D, 114.126156D));		
		listCinema.add(new Coordinate("荃灣", "TSUEN WAN", 22.37124D, 114.111039D));
		
		listCinema.add(new Coordinate("元朗", "元朗", 22.445632D, 114.035822D));	//Reserved till 2013-03-06		
		listCinema.add(new Coordinate("嘉湖銀座", "KINGSWOOD GINZA", 22.457246D, 114.004021D));
		
//		listCinema.add(new Coordinate("", "", , ));
		
	}
	
	
	public static void main(String[] args){		
		
//		Coordinate test = new Coordinate("STAR Cinema (將軍澳站)", "STAR Cinema (Tseung Kwan O Station)");
//		Coordinate test = new Coordinate("STAR Cinema (將軍澳站)");
		Coordinate test = new Coordinate("STAR Cinema (Tseung Kwan O Station)");
		
		int x = listCinema.indexOf(test);
		System.out.println("x:"+x);
		Coordinate result = listCinema.get(x);
		System.out.println("Chinese Name: " + result.getCinemaChinese() + ", English Name: " + result.getCinemaEnglish() + ", X: " + result.getX() + ", Y: " + result.getY());
	}
	
	
}
