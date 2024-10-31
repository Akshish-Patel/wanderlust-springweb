package com.utils;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.Bean.Listing;
import com.Bean.User;
import com.services.MapboxService;

public class data {

	String title;
	String description;
	String url;
	Integer price;
	String location;
	String country;
	
	public static void main(String[] args) throws SQLException {
		
//		dbconnection
		String URLNAME = "jdbc:mysql://localhost:3306/wanderlust";
		String DRIVERCLASS = "com.mysql.cj.jdbc.Driver";
		String USERNAME = "root";
		String PASSWORD = "1234";
		
		Connection conn = null;
		try 
		{
			// 3) load Driver Class
			Class.forName(DRIVERCLASS);
			
			//4) pass credential into DriverManager's getConnection Method
			conn = DriverManager.getConnection(URLNAME, USERNAME, PASSWORD);
			
			System.out.println("connected");
			
		} catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	
		ArrayList<data> arrobj = new ArrayList();
		
		String dataStr = "(\"Cozy Beachfront Cottage\",, \"Escape to this charming beachfront cottage for a relaxing getaway. Enjoy stunning ocean views and easy access to the beach.\",, \"https://images.unsplash.com/photo-1552733407-5d5c46c3bb3b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHRyYXZlbHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 1500,, \"Malibu\",, \"United States\"),,,\r\n"
				+ "(\"Modern Loft in Downtown\",, \"Stay in the heart of the city in this stylish loft apartment. Perfect for urban explorers!\",, \"https://images.unsplash.com/photo-1501785888041-af3ef285b470?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fHRyYXZlbHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 1200,, \"New York City\",, \"United States\"),,,\r\n"
				+ "(\"Mountain Retreat\",, \"Unplug and unwind in this peaceful mountain cabin. Surrounded by nature, it's a perfect place to recharge.\",, \"https://images.unsplash.com/photo-1571896349842-33c89424de2d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8aG90ZWxzfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 1000,, \"Aspen\",, \"United States\"),,,\r\n"
				+ "(\"Historic Villa in Tuscany\",, \"Experience the charm of Tuscany in this beautifully restored villa. Explore the rolling hills and vineyards.\",, \"https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8aG90ZWxzfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 2500,, \"Florence\",, \"Italy\"),,,\r\n"
				+ "(\"Secluded Treehouse Getaway\",, \"Live among the treetops in this unique treehouse retreat. A true nature lover's paradise.\",, \"https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fGhvdGVsc3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 800,, \"Portland\",, \"United States\"),,,\r\n"
				+ "(\"Beachfront Paradise\",, \"Step out of your door onto the sandy beach. This beachfront condo offers the ultimate relaxation.\",, \"https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fGhvdGVsc3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 2000,, \"Cancun\",, \"Mexico\"),,,\r\n"
				+ "(\"Rustic Cabin by the Lake\",, \"Spend your days fishing and kayaking on the serene lake. This cozy cabin is perfect for outdoor enthusiasts.\",, \"https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fG1vdW50YWlufGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 900,, \"Lake Tahoe\",, \"United States\"),,,\r\n"
				+ "(\"Luxury Penthouse with City Views\",, \"Indulge in luxury living with panoramic city views from this stunning penthouse apartment.\",, \"https://images.unsplash.com/photo-1622396481328-9b1b78cdd9fd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c2t5JTIwdmFjYXRpb258ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 3500,, \"Los Angeles\",, \"United States\"),,,\r\n"
				+ "(\"Ski-In/Ski-Out Chalet\",, \"Hit the slopes right from your doorstep in this ski-in/ski-out chalet in the Swiss Alps.\",, \"https://images.unsplash.com/photo-1502784444187-359ac186c5bb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fHNreSUyMHZhY2F0aW9ufGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 3000,, \"Verbier\",, \"Switzerland\"),,,\r\n"
				+ "(\"Safari Lodge in the Serengeti\",, \"Experience the thrill of the wild in a comfortable safari lodge. Witness the Great Migration up close.\",, \"https://images.unsplash.com/photo-1493246507139-91e8fad9978e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mjl8fG1vdW50YWlufGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 4000,, \"Serengeti National Park\",, \"Tanzania\"),,,\r\n"
				+ "(\"Historic Canal House\",, \"Stay in a piece of history in this beautifully preserved canal house in Amsterdam's iconic district.\",, \"https://images.unsplash.com/photo-1504280390367-361c6d9f38f4?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2FtcGluZ3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 1800,, \"Amsterdam\",, \"Netherlands\"),,,\r\n"
				+ "(\"Private Island Retreat\",, \"Have an entire island to yourself for a truly exclusive and unforgettable vacation experience.\",, \"https://images.unsplash.com/photo-1618140052121-39fc6db33972?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8bG9kZ2V8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 10000,, \"Fiji\",, \"Fiji\"),,,\r\n"
				+ "(\"Charming Cottage in the Cotswolds\",, \"Escape to the picturesque Cotswolds in this quaint and charming cottage with a thatched roof.\",, \"https://images.unsplash.com/photo-1602088113235-229c19758e9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8YmVhY2glMjB2YWNhdGlvbnxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 1200,, \"Cotswolds\",, \"United Kingdom\"),,,\r\n"
				+ "(\"Historic Brownstone in Boston\",, \"Step back in time in this elegant historic brownstone located in the heart of Boston.\",, \"https://images.unsplash.com/photo-1533619239233-6280475a633a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTR8fHNreSUyMHZhY2F0aW9ufGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 2200,, \"Boston\",, \"United States\"),,,\r\n"
				+ "(\"Beachfront Bungalow in Bali\",, \"Relax on the sandy shores of Bali in this beautiful beachfront bungalow with a private pool.\",, \"https://images.unsplash.com/photo-1602391833977-358a52198938?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzJ8fGNhbXBpbmd8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 1800,, \"Bali\",, \"Indonesia\"),,,\r\n"
				+ "(\"Mountain View Cabin in Banff\",, \"Enjoy breathtaking mountain views from this cozy cabin in the Canadian Rockies.\",, \"https://images.unsplash.com/photo-1521401830884-6c03c1c87ebb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fGxvZGdlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 1500,, \"Banff\",, \"Canada\"),,,\r\n"
				+ "(\"Art Deco Apartment in Miami\",, \"Step into the glamour of the 1920s in this stylish Art Deco apartment in South Beach.\",, \"https://plus.unsplash.com/premium_photo-1670963964797-942df1804579?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fGxvZGdlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 1600,, \"Miami\",, \"United States\"),,,\r\n"
				+ "(\"Tropical Villa in Phuket\",, \"Escape to a tropical paradise in this luxurious villa with a private infinity pool in Phuket.\",, \"https://images.unsplash.com/photo-1470165301023-58dab8118cc9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fGxvZGdlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 3000,, \"Phuket\",, \"Thailand\"),,,\r\n"
				+ "(\"Historic Castle in Scotland\",, \"Live like royalty in this historic castle in the Scottish Highlands. Explore the rugged beauty of the area.\",, \"https://images.unsplash.com/photo-1585543805890-6051f7829f98?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fGJlYWNoJTIwdmFjYXRpb258ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 4000,, \"Scottish Highlands\",, \"United Kingdom\"),,,\r\n"
				+ "(\"Desert Oasis in Dubai\",, \"Experience luxury in the middle of the desert in this opulent oasis in Dubai with a private pool.\",, \"https://images.unsplash.com/photo-1518684079-3c830dcef090?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8ZHViYWl8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 5000,, \"Dubai\",, \"United Arab Emirates\"),,,     \r\n"
				+ "(\"Rustic Log Cabin in Montana\",, \"Unplug and unwind in this cozy log cabin surrounded by the natural beauty of Montana.\",, \"https://images.unsplash.com/photo-1586375300773-8384e3e4916f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fGxvZGdlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 1100,, \"Montana\",, \"United States\"),,,\r\n"
				+ "(\"Beachfront Villa in Greece\",, \"Enjoy the crystal-clear waters of the Mediterranean in this beautiful beachfront villa on a Greek island.\",, \"https://images.unsplash.com/photo-1602343168117-bb8ffe3e2e9f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8dmlsbGF8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 2500,, \"Mykonos\",, \"Greece\"),,, \r\n"
				+ "(\"Eco-Friendly Treehouse Retreat\",, \"Stay in an eco-friendly treehouse nestled in the forest. It's the perfect escape for nature lovers.\",, \"https://images.unsplash.com/photo-1488462237308-ecaa28b729d7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8c2t5JTIwdmFjYXRpb258ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 750,, \"Costa Rica\",, \"Costa Rica\"),,,\r\n"
				+ "(\"Historic Cottage in Charleston\",, \"Experience the charm of historic Charleston in this beautifully restored cottage with a private garden.\",, \"https://images.unsplash.com/photo-1587381420270-3e1a5b9e6904?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGxvZGdlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 1600,, \"Charleston\",, \"United States\"),,,\r\n"
				+ "(\"Modern Apartment in Tokyo\",, \"Explore the vibrant city of Tokyo from this modern and centrally located apartment.\",, \"https://images.unsplash.com/photo-1480796927426-f609979314bd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fHRva3lvfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=60\",, 2000,, \"Tokyo\",, \"Japan\"),,,\r\n"
				+ "(\"Lakefront Cabin in New Hampshire\",, \"Spend your days by the lake in this cozy cabin in the scenic White Mountains of New Hampshire.\",, \"https://images.unsplash.com/photo-1578645510447-e20b4311e3ce?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDF8fGNhbXBpbmd8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 1200,, \"New Hampshire\",, \"United States\"),,,\r\n"
				+ "(\"Luxury Villa in the Maldives\",, \"Indulge in luxury in this overwater villa in the Maldives with stunning views of the Indian Ocean.\",, \"https://images.unsplash.com/photo-1439066615861-d1af74d74000?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8bGFrZXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 6000,, \"Maldives\",, \"Maldives\"),,, \r\n"
				+ "(\"Ski Chalet in Aspen\",, \"Hit the slopes in style with this luxurious ski chalet in the world-famous Aspen ski resort.\",, \"https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fGxha2V8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=60\",, 4000,, \"Aspen\",, \"United States\"),,,\r\n"
				+ "(\"Secluded Beach House in Costa Rica\",, \"Escape to a secluded beach house on the Pacific coast of Costa Rica. Surf, relax, and unwind.\",, \"https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YmVhY2glMjBob3VzZXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=60\",, 1800,, \"Costa Rica\",, \"Costa Rica\"),,,";
		
		String[] splitData = dataStr.split("\r\n");
//		System.out.println(splitData.length);
		
		String InputeQuary = "insert into listing(title, description, image, price, location, country, owner, coordinate) value(?,?,?,?,?,?,?,?)";
		PreparedStatement pstat = null;
		
		pstat = conn.prepareStatement("drop table review");
		pstat.execute();
		System.out.println("drop reivew table");
		
		pstat = conn.prepareStatement("truncate table listing");
		pstat.execute();
		System.out.println("removed all data");
		
//		users query code
		pstat = conn.prepareStatement("select * from user where username = 'Akshish'"); 
		User user =  new User();
		
		ResultSet rs = pstat.executeQuery();
		rs.next();
		//System.out.println(rs.getString("id"));
		
		for(int i=0;i<splitData.length;i++)
		{
			String singleData = splitData[i].replace(",,,", "").replace("(", "").replace(")", "");
			data d = new data();
			
			
			d.title = singleData.split(",,")[0].replace("\"", "");
			d.description = singleData.split(",,")[1].replace("\"", "");
			d.url = singleData.split(",,")[2].replace("\"", "");
			d.price = Integer.parseInt(singleData.split(",,")[3].trim());
			d.location = singleData.split(",,")[4].replace("\"", "");
			d.country = singleData.split(",,")[5].replace("\"", "");
//			System.out.println(d.title + " " + d.description + " " + d.url + " " + d.price + " " + d.location + " " + d.country);
//			arrobj.add(d);
			
			mapbox mapbox = new mapbox();
//			System.out.println(mapbox.getCoordinates(d.location).toString().replace("[", "").replace("]", "").split(","));
			String coordinates = mapbox.getCoordinates(d.location).toString().replace("[", "").replace("]", "");
			
			
			if(conn != null)
			{
				try {
					pstat = conn.prepareStatement(InputeQuary);
					pstat.setString(1, d.title);
					pstat.setString(2, d.description);
					pstat.setString(3, d.url);
					pstat.setInt(4, d.price);
					pstat.setString(5, d.location);
					pstat.setString(6, d.country);
					pstat.setInt(7, rs.getInt("id"));
					pstat.setString(8, coordinates);
					pstat.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}else {
				System.out.println("EmployeeDao--Insert--Database Connection is failed!! " + conn);
			}

			
		}
		System.out.println("add all data");
		
		
	
	
	}
	
}
