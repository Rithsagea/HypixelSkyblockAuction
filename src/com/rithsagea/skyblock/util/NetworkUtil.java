package com.rithsagea.skyblock.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.rithsagea.skyblock.Auction;
import com.rithsagea.skyblock.SecureConstants;
import com.rithsagea.skyblock.format.AuctionFormat;
import com.rithsagea.skyblock.format.RequestFormat;

public class NetworkUtil {
	
	public static final String link = "https://api.hypixel.net/skyblock/auctions?key=" + SecureConstants.key + "&page=";
	
	public static final StringWriter writer = new StringWriter();
	
	public static InputStream getAuction(int page) throws IOException {
//		System.out.print("Auction: " + page + " |\t");
		return new URL(link + page).openConnection().getInputStream();
	}
	
	//returns whether it worked
	public static boolean readAuctionPage(InputStream auction, HashMap<UUID, Auction> auctions) throws IOException {
		RequestFormat request = new Gson().fromJson(new InputStreamReader(auction), RequestFormat.class);
		auction.close();
		if(request.success.equals("false")) {
			return false;
		}
		long time = request.lastUpdated;
		for(AuctionFormat au : request.auctions) {
			if(au.highest_bid_amount > 0 && au.end - time <= 120000) { //2 minutes to milliseconds
				Auction item = new Auction(au);
				auctions.put(item.id, item);
			}
		}
		return true;
	}
}
