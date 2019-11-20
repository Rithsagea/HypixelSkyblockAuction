package com.rithsagea.skyblock;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;

public class Logger {
	private static final StringBuilder builder = new StringBuilder();
	private static final Deque<String> messages = new ArrayDeque<String>(42);
	private static final Calendar calendar = Calendar.getInstance();
	
	public static void log(String text) {
		calendar.setTimeInMillis(System.currentTimeMillis());
		messages.add(String.format("[%s] %s\n", calendar.getTime(), text));
		if(messages.size() > 42)
			messages.remove();
		//Possibly replace with something better
		Main.log.setText(Logger.getText());
	}
	
	public static String getText() {
		builder.setLength(0);
		messages.iterator().forEachRemaining(builder::append);
		return builder.toString();
	}
}
