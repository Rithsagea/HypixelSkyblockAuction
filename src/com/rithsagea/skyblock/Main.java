package com.rithsagea.skyblock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Main {
	
	public static final String key =  "";
	public static final String link = "https://api.hypixel.net/skyblock/auctions?key=" + key + "&page=";

	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss.SSS");
	
	public static ArrayList<Auction> auctions = new ArrayList<Auction>();

	public static void main(String[] args) throws IOException {
		URL url = new URL(link + 0
				);
		System.out.println("Program Start");
		System.out.println("Connecting to hypixel with key " + key);
		URLConnection con = url.openConnection();
		System.out.println("Connection Established");
		InputStream is = con.getInputStream();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("auctions/" + dateFormat.format(new Date()) + ".json"));
		BufferedReader br = new BufferedReader (new InputStreamReader(is));

//		Gson gson = new Gson();
//		System.out.println("Parsing JSON");
//		RequestFormat request = gson.fromJson(br, RequestFormat.class);
//
//		for(AuctionFormat au : request.auctions) {
//			if(au.highest_bid_amount > 0) {
//				Auction item = new Auction(au);
//				System.out.println(item);
//				auctions.add(item);
//			}
//		}

		Gson test = new GsonBuilder().setPrettyPrinting().create();
		System.out.println("Parsing Json");
		JsonElement el = JsonParser.parseString(br.readLine());
		System.out.println("Writing to auction.json");
		bw.write(test.toJson(el));
		bw.close();
		System.out.println("\n\nProgram Finished");
	}
}