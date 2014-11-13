package movieservice.util;

import java.util.ArrayList;

public class ConstantUtil {
	
	public static final String LANG_CHI = "zh";
	public static final String LANG_ENG = "en";	
	private static final String MCL = "MCL";
	public static final String BIG5 = "Big5";
		
	//public static HashMap<String, ArrayList<Coordinate>> mapCinema = new HashMap<String, ArrayList<Coordinate>>();
	public static final ArrayList<Coordinate> listCinema = new ArrayList<Coordinate>();
	public static final ArrayList<CoordinateBroadway> listCinemaBroadway = new ArrayList<CoordinateBroadway>();
	public static final ArrayList<CoordinateTheGrand> listCinemaTheGrand = new ArrayList<CoordinateTheGrand>();	
	public static final ArrayList<CoordinateUA> listCinemaUA = new ArrayList<CoordinateUA>();		
	
	static{
		
		listCinema.add(new Coordinate("MCL JP銅鑼灣戲院", "MCL JP Cinema", 22.280715D, 114.185725D));
		listCinema.add(new Coordinate("MCL 康怡戲院", "MCL Kornhill Cinema", 22.28422D,114.216409D));
		listCinema.add(new Coordinate("MCL 將軍澳戲院 (寶琳站)", "MCL Metro Cinema (Po Lam Station)", 22.323244D, 114.258037D));		
		listCinema.add(new Coordinate("MCL 德福戲院", "MCL Telford Cinema", 22.322882D, 114.212257D));		
		listCinema.add(new Coordinate("STAR Cinema (將軍澳站)", "STAR Cinema (Tseung Kwan O Station)", 22.308468D, 114.260551D));
		
		listCinemaTheGrand.add(new CoordinateTheGrand("The Grand Cinema", "The Grand Cinema", 22.304776D, 114.161789D));
		
		
		listCinema.add(new Coordinate("the sky (奧海城)", "the sky Olympian City", 22.316798D, 114.161513D));
		listCinema.add(new Coordinate("海運戲院 (超巨幕)", "Grand Ocean (Xtreme)", 22.295256D, 114.169377D));		
		listCinema.add(new Coordinate("嘉禾港威", "Golden Gateway", 22.299378D, 114.167961D));		
		listCinema.add(new Coordinate("嘉禾青衣", "GH Tsing Yi", 22.359338D, 114.108255D));		
		listCinema.add(new Coordinate("嘉禾荃新天地", "GH Citywalk", 22.367732D, 114.114832D));
		listCinema.add(new Coordinate("嘉禾黃埔", "GH Whampoa", 22.304686D, 114.19035D));
		
		listCinemaBroadway.add(new CoordinateBroadway("PALACE ifc", "PALACE ifc", "PALACE ifc", "PALACE ifc", 22.285537D, 114.158155D));
		listCinemaBroadway.add(new CoordinateBroadway("數碼港", "CYBERPORT", "百老匯 數碼港", "Broadway CYBERPORT", 22.261037D, 114.129453D));		
		listCinemaBroadway.add(new CoordinateBroadway("荷里活", "HOLLYWOOD", "百老匯 荷里活", "Broadway HOLLYWOOD", 22.340604D, 114.202092D));		
		listCinemaBroadway.add(new CoordinateBroadway("The ONE", "The ONE", "The ONE", "The ONE", 22.29979D, 114.172172D));		
		listCinemaBroadway.add(new CoordinateBroadway("電影中心", "CINEMATHEQUE", "電影中心", "CINEMATHEQUE", 22.310694D, 114.168913D));		
		listCinemaBroadway.add(new CoordinateBroadway("旺角", "MONGKOK", "百老匯 旺角", "Broadway MONGKOK", 22.317116D, 114.170627D));		
		listCinemaBroadway.add(new CoordinateBroadway("PALACE  apm", "PALACE  apm", "PALACE  apm", "PALACE  apm", 22.312299D, 114.225355D));		
		listCinemaBroadway.add(new CoordinateBroadway("葵芳", "KWAI FONG", "百老匯 葵芳", "Broadway KWAI FONG", 22.357456D, 114.126156D));	
		listCinemaBroadway.add(new CoordinateBroadway("荃灣", "TSUEN WAN", "百老匯 荃灣", "Broadway TSUEN WAN", 22.37124D, 114.111039D));		
		listCinemaBroadway.add(new CoordinateBroadway("元朗", "元朗", "百老匯 元朗", "Broadway 元朗", 22.445632D, 114.035822D));	//Reserved till 2013-03-06		
		listCinemaBroadway.add(new CoordinateBroadway("嘉湖銀座", "KINGSWOOD GINZA", "百老匯 嘉湖銀座", "Broadway KINGSWOOD GINZA", 22.457246D, 114.004021D));
		
		listCinema.add(new Coordinate("AMC 又一城", "AMC Festival Walk", 22.337535D, 114.173725D));		
		listCinema.add(new Coordinate("AMC Pacific Place", "AMC Pacific Place", 22.277414D, 114.166308D));		
//		listCinema.add(new Coordinate("", "", , ));
		
		listCinemaUA.add(new CoordinateUA("UA Director's Club", "UA Director's Club", "11", 22.286528D, 114.217421D));
		listCinemaUA.add(new CoordinateUA("UA 太古城中心", "UA Cityplaza", "7", 22.286528D, 114.217421D));
		listCinemaUA.add(new CoordinateUA("皇室戲院", "Windsor Cinema", "10", 22.280608D, 114.186774D));
		listCinemaUA.add(new CoordinateUA("新光戲院大劇場", "Sunbeam Theatre", "12", 22.29128D, 114.199963D));		
		listCinemaUA.add(new CoordinateUA("BEA IMAX @ UA iSQUARE", "BEA IMAX @ UA iSQUARE", "19", 22.297033D, 114.17188D));
		listCinemaUA.add(new CoordinateUA("BEA IMAX @ UA MegaBox", "BEA IMAX @ UA MegaBox", "15", 22.319813D, 114.208741D));
		listCinemaUA.add(new CoordinateUA("UA 朗豪坊", "UA Langham Place", "9", 22.317565D, 114.168779D));		
		listCinemaUA.add(new CoordinateUA("UA iSQUARE", "UA iSQUARE", "18", 22.297033D, 114.17188D));		
		listCinemaUA.add(new CoordinateUA("UA MegaBox", "UA MegaBox", "14", 22.319813D, 114.208741D));		
		listCinemaUA.add(new CoordinateUA("影藝戲院", "Cine-Art House", "8", 22.324018D, 114.21704D));		
		listCinemaUA.add(new CoordinateUA("機場 UA IMAX 影院", "UA IMAX Theatre @ Airport", "22", 22.317103D, 113.937508D));		
		listCinemaUA.add(new CoordinateUA("UA 沙田", "UA Shatin", "1", 22.380732D, 114.188147D));		
		listCinemaUA.add(new CoordinateUA("UA 屯門市廣場", "UA tmtplaza", "17", 22.393452D, 113.976591D));
		listCinemaUA.add(new CoordinateUA("UA 東薈城", "UA Citygate", "6", 22.28957D, 113.940351D));
		listCinemaUA.add(new CoordinateUA("元朗戲院", "Yuen Long Cinema", "21", 22.445672D, 114.028961D));
		
//		listCinemaUA.add(new CoordinateUA("", "", "", , ));		
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
