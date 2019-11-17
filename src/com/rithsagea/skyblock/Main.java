package com.rithsagea.skyblock;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.rithsagea.skyblock.util.DatabaseUtil;

public class Main {
	public static HashMap<UUID, Auction> auctions = new HashMap<UUID, Auction>();
	
	public static void main(String[] args) throws IOException, SQLException {
		//connect to mysql database
		DatabaseUtil.connectToDB();
		
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new DataDownloaderRunnable(), 0, 1, TimeUnit.MINUTES);
	}
}