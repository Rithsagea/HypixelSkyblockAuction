package com.rithsagea.skyblock;

import java.io.IOException;

public class Auction {
	public String item_name;
	public String item_lore;
	public int amount;
	public long start;
	public long end;
	public double price;
	
	public Auction(AuctionFormat au) throws IOException {
		this.item_name = au.item_name;
		this.start = au.start;
		this.end = au.end;
		this.price = au.highest_bid_amount;
		
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
	}
	
	public String toString() {
		return String.format("\n--- %s ---\n%s\n\nPrice: %.2f coins\n\nStart:\t%d\nEnd:\t%d", item_name, item_lore, price, start, end);
	}
}
