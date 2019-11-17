package com.rithsagea.skyblock;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import com.rithsagea.skyblock.util.DatabaseUtil;
import com.rithsagea.skyblock.util.NetworkUtil;

public class DataDownloaderRunnable implements Runnable {
	public static HashMap<UUID, Auction> auctions = new HashMap<UUID, Auction>();
	
	@Override
	public void run() {
		//get literally every single auction in existence (that's less than 2 minutes long)
		auctions.clear();
		try {
			System.out.println("Getting auctions from hypixel.net");
			for(int x = 0; NetworkUtil.readAuctionPage(NetworkUtil.getAuction(x), auctions); x++);
			System.out.println("\nSuccesfully grabbed all " + auctions.size() + " auctions");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//writes all the about to end auctions to the database
		DatabaseUtil.writeToTable(auctions, "auction_log");
		System.out.println("run finished\n\n\n");
	}
}
