package com.rithsagea.skyblock.util;

import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import com.rithsagea.skyblock.Auction;

public class CalcUtil {
	
	public static Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
	
	public static void weightedAverage(Set<Auction> auctions) {
		double[] item_count = new double[24];
		double[] price_total = new double[24];
		
		int hour = 0;
		
		for(Auction auction : auctions) {
			calendar.setTimeInMillis(auction.end);
			hour = calendar.get(Calendar.HOUR_OF_DAY);
			price_total[hour] += auction.price * auction.amount;
			item_count[hour] += auction.amount;
		}
		
		for(int x = 0; x < 24; x++) {
			System.out.format("%d:00 - %.2f\n", x, price_total[x] / item_count[x]);
		}
	}
}
