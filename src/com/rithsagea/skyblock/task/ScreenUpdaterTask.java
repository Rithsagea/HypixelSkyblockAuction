package com.rithsagea.skyblock.task;

import java.util.TimerTask;

import com.rithsagea.skyblock.Logger;
import com.rithsagea.skyblock.Main;
import com.rithsagea.skyblock.util.DatabaseUtil;

public class ScreenUpdaterTask extends TimerTask {

	@Override
	public void run() {
		Main.log.setText(Logger.getText());
		Main.downloaderInfo.setText(String.format("<html>New Entries: %d<br>Requests Made: %d</html>", DatabaseUtil.new_items, DataDownloaderTask.requests));	
	}
}
