package com.rithsagea.skyblock;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.rithsagea.skyblock.graphing.FocusingTextField;
import com.rithsagea.skyblock.graphing.SkyblockFrame;
import com.rithsagea.skyblock.task.DataDownloaderTask;
import com.rithsagea.skyblock.task.ScreenUpdaterTask;
import com.rithsagea.skyblock.util.CalcUtil;
import com.rithsagea.skyblock.util.DatabaseUtil;

public class Main {
	public static HashMap<UUID, Auction> auctions = new HashMap<UUID, Auction>();
	public static Deque<String> averageResults = new ArrayDeque<String>();
	
	public static DataDownloaderTask downloaderTask = new DataDownloaderTask();
	public static ScreenUpdaterTask screenTask = new ScreenUpdaterTask();
	public static Timer timer = new Timer();
	
	public static JButton dataDownloadToggle;
	public static JButton averageCalculateActivator;
	public static JButton movingAverageActivator;
	public static JLabel downloaderInfo;
	public static FocusingTextField itemFilterField;
	public static JTextArea averageCalculateResult;
	public static JTextArea log;
	
	public static void registerComponents() {
		dataDownloadToggle = new JButton(downloaderTask.status ? "Downloading On" : "Downloading Off");
		dataDownloadToggle.setBounds(10, 10, 240, 120);
		dataDownloadToggle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(downloaderTask.status) {
					Logger.log("Downloads have been turned off.");
					dataDownloadToggle.setText("Downloading Off");
					downloaderTask.status = false;
				} else {
					Logger.log("Downloads have been turned on.");
					dataDownloadToggle.setText("Downloading On");
					downloaderTask.status = true;
				}
			}
		});
		
		log = new JTextArea(1, 1);
		log.setBounds(260, 10, 500, 680);
//		log.setEditable(false);
		
		downloaderInfo = new JLabel();
		downloaderInfo.setBounds(10, 140, 240, 120);
		
		itemFilterField = new FocusingTextField("Enter the item name here...");
		itemFilterField.setBounds(10, 280, 240, 20);
		
		averageCalculateActivator = new JButton("Calculate Price");
		averageCalculateActivator.setBounds(10, 310, 240, 20);
		averageCalculateActivator.addActionListener(new ActionListener() {
			
			private final StringBuilder builder = new StringBuilder();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					ResultSet result = DatabaseUtil.statement
							.executeQuery(
							"select sum(price) / sum(amount) "
							+ "from auction_log "
							+ "where item_name like "
							+ "\"%" + itemFilterField.getText() + "%\"");
					result.next();
					averageResults.add(String.format("%s: %.2f\n", itemFilterField.getText(), result.getDouble(1)));
					if(averageResults.size() > 10)
						averageResults.remove();
					builder.setLength(0);
					averageResults.descendingIterator().forEachRemaining(builder::append);
					averageCalculateResult.setText(builder.toString());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		movingAverageActivator = new JButton("Calculate Moving Average");
		movingAverageActivator.setBounds(10, 340, 240, 20);
		movingAverageActivator.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Set<Auction> data = new HashSet<Auction>();
				DatabaseUtil.readFromTable(data, itemFilterField.getText(), "auction_log");
				System.out.println("-=-=- " + itemFilterField.getText() + " -=-=-\n");
				CalcUtil.weightedAverage(data);
			}
		});
		
		averageCalculateResult = new JTextArea(1, 10);
		averageCalculateResult.setBounds(10, 370, 240, 160);
		averageCalculateResult.setEditable(false);
		averageCalculateResult.setBackground(null);
//		averageCalculateResult.setBorder(null);
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		//window code here
		Frame frame = new SkyblockFrame("Skyblock Auction \"Hacker\"");

		registerComponents();
		frame.add(dataDownloadToggle);
		frame.add(downloaderInfo);
		frame.add(itemFilterField);
		frame.add(log);
		frame.add(averageCalculateActivator);
		frame.add(averageCalculateResult);
		frame.add(movingAverageActivator);
		
		frame.setSize(1080, 720);
		frame.setLayout(null);
		frame.setVisible(true);
		
		//connect to mysql database
		DatabaseUtil.connectToDB();
		
		//start runnable
		timer.scheduleAtFixedRate(downloaderTask, 0, 60000);
		timer.scheduleAtFixedRate(screenTask, 0, 100);
	}
}