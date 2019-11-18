package com.rithsagea.skyblock;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.rithsagea.skyblock.task.DataDownloaderTask;
import com.rithsagea.skyblock.task.ScreenUpdaterTask;
import com.rithsagea.skyblock.util.DatabaseUtil;

public class Main {
	public static HashMap<UUID, Auction> auctions = new HashMap<UUID, Auction>();
	
	public static DataDownloaderTask downloaderTask = new DataDownloaderTask();
	public static ScreenUpdaterTask screenTask = new ScreenUpdaterTask();
	public static Timer timer = new Timer();
	
	public static JButton dataDownloadToggle;
	public static JTextArea log;
	public static JLabel downloaderInfo;
	
	public static void log(String text) {
		log.append(text);
	}
	
	public static void registerComponents() {
		dataDownloadToggle = new JButton("Downloading Off");
		dataDownloadToggle.setBounds(10, 10, 120, 120);
		dataDownloadToggle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(downloaderTask.status) {
					dataDownloadToggle.setText("Downloading Off");
					downloaderTask.status = false;
				} else {
					dataDownloadToggle.setText("Downloading On");
					downloaderTask.status = true;
				}
			}
		});
		
		log = new JTextArea(30, 30);
		log.setBounds(140, 10, 360, 680);
		
		downloaderInfo = new JLabel();
		downloaderInfo.setBounds(10, 140, 120, 120);
	}
	
	public static void main(String[] args) throws IOException, SQLException {
		
		//window code here
		Frame frame = new JFrame("Skyblock Auction \"Hacker\"");
		
		frame.setSize(1080, 720);
		registerComponents();
		frame.add(dataDownloadToggle);
		frame.add(log);
		frame.add(downloaderInfo);
		frame.setLayout(null);
		frame.setVisible(true);
		
		//connect to mysql database
		DatabaseUtil.connectToDB();
		
		//start runnable
		timer.scheduleAtFixedRate(downloaderTask, 0, 60000);
		timer.scheduleAtFixedRate(screenTask, 0, 50);
	}
}