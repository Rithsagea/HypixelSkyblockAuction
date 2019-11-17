package com.rithsagea.skyblock.util;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.Base64.Encoder;

import com.rithsagea.skyblock.Auction;
import com.rithsagea.skyblock.SecureConstants;

public class DatabaseUtil {
	public static Connection dbCon;
	public static Statement statement;
	
	public static Auction auction;
	public static ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
	public static Encoder encoder = Base64.getEncoder();
	
	public static final String update = "update %s set end_time=%d, price=%f where id like from_base64('%s')";
	public static final String format = "insert into %s (id, item_name, item_lore, amount, start_time, end_time, price) "
			+ "values(from_base64('%s'), \"%s\", \"%s\", %d, %d, %d, %f)";
	
	public static void connectToDB() {
		System.out.println("Connecting to database");
		try {
			dbCon = DriverManager.getConnection("jdbc:mysql://" + SecureConstants.databaseLink + "/" + SecureConstants.table + "?"+ 
												"user=" + SecureConstants.user + "&password=" + SecureConstants.password);
			statement = dbCon.createStatement();
			System.out.println("Succesfully connected to db skyblock");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    System.exit(0);
		}
	}
	
	public static void writeToTable(HashMap<UUID, Auction> data, String tableName) {
		System.out.println("Writing " + data.size() + " entries into " + tableName);
		
		int duplicates = 0;
		int new_items = 0;
		
		for(UUID id : data.keySet()) {
			try {
				auction = data.get(id);
				buffer.clear();
				buffer.putLong(id.getMostSignificantBits());
				buffer.putLong(id.getLeastSignificantBits());
				String encodedString = new String(encoder.encode(buffer.array()));
				if(statement.executeQuery("select * from " + tableName + " where id like from_base64('" + encodedString + "')").next()) {
					String command = String.format(update, tableName, auction.end, auction.price, encodedString);
					statement.execute(command);
					duplicates++;
				} else {
					statement.execute(String.format(format,
												tableName,
												encodedString,
												auction.item_name,
												auction.item_lore,
												auction.amount,
												auction.start,
												auction.end,
												auction.price));
					new_items++;
				}
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
			    System.out.println("SQLState: " + e.getSQLState());
			    System.out.println("VendorError: " + e.getErrorCode());
			}
		}
		System.out.println("Edited " + duplicates + " entries");
		System.out.println("Created " + new_items + " new items");
	}
	
	public static void readFromTable(HashMap<UUID, Auction> data, String query) {
		try {
			ResultSet results = statement.executeQuery(query);
			while(results.next()) {
				Auction a = new Auction(
						UUID.nameUUIDFromBytes(results.getBytes("id")),
						results.getString("item_name"),
						results.getString("item_lore"),
						results.getShort("amount"),
						results.getLong("start_time"),
						results.getLong("end_time"),
						results.getDouble("price"));
				data.put(a.id, a);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
	}
}