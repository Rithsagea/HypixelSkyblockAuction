package com.rithsagea.skyblock.format;

public class AuctionFormat {
	public String uuid;
	public String auctioneer;
	public String profile_id;
	public String[] coop;
	public long start;
	public long end;
	public String item_name;
	public String item_lore;
	public String extra;
	public String category;
	public String tier;
	public double starting_bid;
	public String item_bytes;
	public boolean claimed;
	public String[] claimed_bidders;
	public double highest_bid_amount;
	public BidFormat[] bids;
}
