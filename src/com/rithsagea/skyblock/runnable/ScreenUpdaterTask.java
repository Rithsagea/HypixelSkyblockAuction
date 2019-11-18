package com.rithsagea.skyblock.runnable;

import java.util.TimerTask;

import com.rithsagea.skyblock.Main;
import com.rithsagea.skyblock.util.DatabaseUtil;

public class ScreenUpdaterTask extends TimerTask {

	@Override
	public void run() {
		Main.downloaderInfo.setText(String.format("<html>New Entries: %d</html>", DatabaseUtil.new_items));	
	}
}
