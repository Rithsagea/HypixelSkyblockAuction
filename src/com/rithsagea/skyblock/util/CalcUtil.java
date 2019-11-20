package com.rithsagea.skyblock.util;

import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import com.rithsagea.skyblock.Auction;
import com.rithsagea.skyblock.Logger;

public class CalcUtil {
	
	
	//PST - California
	//EST - New York
	public static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("EST"));
	
	public static void weightedAverage(Set<Auction> auctions) {
		double[] item_count = new double[24];
		double[] averages = new double[24];
		
		int hour = 0;
		
		for(Auction auction : auctions) {
			
			calendar.setTimeInMillis(auction.end);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			averages[hour] += auction.price;
			item_count[hour] += auction.amount;
		}
		
		int max = 0;
		int min = 0;
		
		for(int x = 0; x < 24; x++) {
			averages[x] /= item_count[x];
			if(averages[x] > averages[max]) max = x;
			if(averages[x] < averages[min]) min = x;
			Logger.log(String.format("%d:00 - %.2f", x, averages[x]));
		}
		
		Logger.log(String.format("Highest Price: %d:00 - %.2f", max, averages[max]));
		Logger.log(String.format("Lowest Price: %d:00 - %.2f", min, averages[min]));
	}
}
