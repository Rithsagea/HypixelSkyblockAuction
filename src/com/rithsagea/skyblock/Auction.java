package com.rithsagea.skyblock;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import com.rithsagea.skyblock.format.AuctionFormat;
import com.rithsagea.skyblock.util.Util;

public class Auction {
	
	public static DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG);
	
	public UUID id;
	public String item_name;
	public String item_lore;
	public short amount;
	public long start;
	public long end;
	public double price;
	
	public Auction(UUID id, String item_name, String item_lore, short amount, long start, long end, double price) {
		this.id = id;
		this.item_name = item_name;
		this.item_lore = item_lore;
		this.amount = amount;
		this.start = start;
		this.end = end;
		this.price = price;
	}
	
	public Auction(AuctionFormat au) throws IOException {
		this.item_name = au.item_name;
		this.start = au.start;
		this.end = au.end;
		this.price = au.highest_bid_amount;
		this.amount = 1;
		
		id = Util.stringToUUID(au.uuid);
		
		//read lore
		char[] array = au.item_lore.toCharArray();
		StringBuilder builder = new StringBuilder();
		
		for(int x = 0; x < array.length; x++) {
			if(array[x] == '§')
				x+= 1;
			else
				builder.append(array[x]);
		}
		
		this.item_lore = builder.toString();
		
		//get item count here
		amount = new Item(au.item_bytes).count;
	}
	
	public String toString() {
		return String.format("\n--- %s x%d ---\n%s\n\nPrice: %.2f coins\n\nStart:\t%s\nEnd:\t%s", 
				item_name,
				amount,
				item_lore,
				price,
				format.format(new Date(start)),
				format.format(new Date(end)));
	}
}
