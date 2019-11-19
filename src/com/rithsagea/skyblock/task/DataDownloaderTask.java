package com.rithsagea.skyblock.task;

import java.io.IOException;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

import com.rithsagea.skyblock.Auction;
import com.rithsagea.skyblock.util.DatabaseUtil;
import com.rithsagea.skyblock.util.NetworkUtil;

public class DataDownloaderTask extends TimerTask {
	public HashMap<UUID, Auction> auctions = new HashMap<UUID, Auction>();
	public long time = 0;
	public boolean status = false;
	
	public static int requests = 0;
	
	@Override
	public void run() {
		if(status) {
			requests++;
			time = System.currentTimeMillis();
			//get literally every single auction in existence (that's less than 2 minutes long)
			auctions.clear();
			try {
				for(int x = 0; NetworkUtil.readAuctionPage(NetworkUtil.getAuction(x), auctions); x++);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//writes all the about to end auctions to the database
			DatabaseUtil.writeToTable(auctions, "auctions");
			
			//moves stuff from temp storage every 30 requests
			if(requests % 30 == 0)
				DatabaseUtil.databaseTransfer();
		}
	}
}
