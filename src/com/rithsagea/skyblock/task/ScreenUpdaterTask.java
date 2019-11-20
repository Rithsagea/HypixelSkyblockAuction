package com.rithsagea.skyblock.task;

import java.util.TimerTask;

import com.rithsagea.skyblock.Main;
import com.rithsagea.skyblock.util.DatabaseUtil;

public class ScreenUpdaterTask extends TimerTask {

	@Override
	public void run() {
		Main.downloaderInfo.setText(String.format("<html>Total Entries: %d<br>New Entries: %d<br>Requests Made: %d</html>", DatabaseUtil.total_items, DatabaseUtil.new_items, DataDownloaderTask.requests));
	}
}
