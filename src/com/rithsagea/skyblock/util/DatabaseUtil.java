package com.rithsagea.skyblock.util;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import com.rithsagea.skyblock.Auction;
import com.rithsagea.skyblock.Logger;
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
	public static final String read = "select * from %s where item_name like \"%s\"";
	
	public static int new_items = 0;
	public static long total_items = 0;
	
	public static void connectToDB() {
		Logger.log("Connecting to database");
		try {
			dbCon = DriverManager.getConnection("jdbc:mysql://" + SecureConstants.databaseLink + "/" + SecureConstants.table + "?"+ 
												"user=" + SecureConstants.user + "&password=" + SecureConstants.password);
			statement = dbCon.createStatement();
			Logger.log("Succesfully connected auction database");
			statement.execute("truncate auctions");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		total_items = getTotalItems("auction_log");
	}
	
	public static long getTotalItems(String tableName) {
		try {
			ResultSet results = statement.executeQuery("select count(*) from " + tableName);
			results.next();
			return results.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static void writeToTable(HashMap<UUID, Auction> data, String tableName) {
		Logger.log("Writing " + data.size() + " auctions to temp database");
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
				e.printStackTrace();
			}
		}
	}
	
	public static void readFromTable(Set <Auction> data, String query, String tableName) {
		try {
			ResultSet results = statement.executeQuery(String.format(read, tableName, query));
			while(results.next()) {
				Auction a = new Auction(
						UUID.nameUUIDFromBytes(results.getBytes("id")),
						results.getString("item_name"),
						results.getString("item_lore"),
						results.getShort("amount"),
						results.getLong("start_time"),
						results.getLong("end_time"),
						results.getDouble("price"));
				data.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void databaseTransfer() {
		Logger.log("Transfering auctions from temp database");
		try {
			statement.execute("insert into auction_log\n" + 
					"select * from auctions\n" + 
					"where end_time < " + (System.currentTimeMillis() - 300000));
			statement.execute("delete from auctions\n" + 
					"where end_time < " + (System.currentTimeMillis() - 300000));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		total_items = getTotalItems("auction_log");
	}
}
